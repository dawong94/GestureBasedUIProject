package marlin.reaction;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import marlin.graphicslib.I;
import marlin.graphicslib.UC;
import marlin.graphicslib.G;

public class Ink extends G.PL implements I.Show {

  /*
  BUFFER变量声明为静态，是为了整个程序运行中，所有Ink的实例都共享这一个Buffer对象，减少不必要的内存消耗。
  课堂上，老师提到了Singleton即单件模式，并将下面Buffer子类的构造器改为private访问权限。
  但是我认为，此处修改并没有发挥作用：
  第一，将BUFFER声明为static已经确保了无论有多少个Int实例，Buffer对象仅此一个，与构造器访问权限无关。
  第二，如果此处不用static，仅仅把Buffer类构造器改为private，也不是正确的单件模式写法。这是因为：
  Buffer是Ink的内部类，内部类无论private还是public，外部类均可以访问，私有化内部类构造器没有起到单件作用。
  正确方式应该是在Buffer类写一个createBuffer方法，方法体中判断BUFFER变量是否为null。
  然后把下面的赋值语句右面改为createBuffer()。
  createBuffer判断如果为BUFFER为null，则调用Buffer的构造器返回一个新的Buffer对象赋值给BUFFER变量。
  如果BUFFER不为null，则保持BUFFER还是指向BUFFER。
  此为正确的单件模式。当然，如果考虑到多线程，还需要增加控制线程的语句确保线程不出错，此处不涉及，不做引申。
   */
  public static Buffer BUFFER = new Buffer();

  /*
  构造器的作用在于每次画完一个图形鼠标释放时，把当前BUFFER中储存的图形保存为一个新的Int对象。
   */
  public Ink() {
    super(BUFFER.n);
    for (int i = 0; i < BUFFER.n; i++) {
      this.points[i].set(BUFFER.points[i]);
    }
  }

  /*
  此方法调用继承自G.PL的draw方法，即把当前Int对象保存的所有点都画出来。
   */
  @Override
  public void show(Graphics g) {
    draw(g);
  }

  public static class inkList extends ArrayList<Ink> implements I.Show {

    /*
    即把inkList对象列表中储存的所有Ink对象（即线条图形）都画出来。
     */
    @Override
    public void show(Graphics g) {
      for (Ink ink : this) {
        ink.show(g);
      }
    }
  }

  /*
  Buffer类基本属性与Ink类一致，都继承自G.PL，并且实现I.Show接口。
  唯一却别在于Buffer类还实现了I.Area接口，这是因为Buffer类表达的是鼠标正在绘制的这个图形，需要响应一些动作。
  当鼠标点下去、释放前，所有drag经过的点都保存在Buffer对象里，并且实时显示在屏幕上。
  当鼠标释放时，Buffer对象中保存的所有点复制到一个新的Int对象里，并加到intList，即屏幕上显示的之前画过的线条图形。
   */
  public static class Buffer extends G.PL implements I.Show, I.Area {

    public static final int MAX = UC.inkBufferMax;
    public int n = 0;

    /*
    根据前文所述，此处private改回public效果相同。
     */
    public Buffer() {
      super(MAX);
    }

    /*
    此处的if条件是为了防止数组越界，因为当前Buffer对象保存的每一个点都存在points数组里，数组大小已声明为MAX常量。
    但是固定大小的数组会产生一个限制，就是当鼠标点下持续拖拽绘制图形时，图形的长度受制于MAX，有最大上限。
    如果有必要，在Buffer中可以考虑用ArrayList代替数组来保存绘制的点的集合。
     */
    public void add(int x, int y) {
      if (n < MAX) {
        points[n++].set(x, y);
      }
    }

    public void clear() {
      n = 0;
    }

    @Override
    public boolean hit(int x, int y) {
      return true;
    }

    /*
    此处clear的作用正是因为之前提到Buffer对象BUFFER变量是全局唯一的，每次鼠标点击时要把当前index即n归零。
    因为下面的show方法调用drawN时传递的正是这个n，如果第二次点鼠标画图形时，如果n不清零，则此前的BUFFER剩余线条会保留。
     */
    @Override
    public void pressed(int x, int y) {
      clear();
      add(x, y);
    }

    @Override
    public void dragged(int x, int y) {
      add(x, y);
    }

    @Override
    public void released(int x, int y) {

    }

    @Override
    public void show(Graphics g) {
      g.setColor(Color.GREEN);
      /*
     课上最后一个bug（即每次画图形时都有冗余线条出现）出在这里。
     老师之所以debug后drawN代替draw方法，是因为只需要在窗口画出Buffer对象中序号n之前保存的点。
     这是因为每次Buffer清零clear方法只是清零了序号n，也就是说大于序号n的位置其实保存有此前图形记录的。
       */
      drawN(g, n);
    }
  }
}
