package fawda.design.prototypepattern.deep;


import java.io.*;
import java.util.Date;

/**
 * Created by Tom.
 */
public class QiTianDaSheng extends Monkey implements Cloneable,Serializable {

    public JinGuBang jinGuBang;

    public  QiTianDaSheng(){
        //只是初始化
        this.birthday = new Date();
        this.jinGuBang = new JinGuBang();
    }

    //克隆方法，采用深克隆
    //可能会出现单例被破坏的问题
    //克隆与单例冲突，
    //如果cope的是单例需要做以下操作：不实现Cloneable或者实现readResolve
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    protected Object deepClone(){
        try{

            //在内存中完成操作，对象读写是通过字节码之间操作
            //序列化操作类似
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            //完整的对象，new出来一个对象
            QiTianDaSheng copy = (QiTianDaSheng)ois.readObject();
            copy.birthday = new Date();
            return copy;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public QiTianDaSheng shallowClone(QiTianDaSheng target){

        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
        qiTianDaSheng.height = target.height;
        qiTianDaSheng.weight = target.height;

        qiTianDaSheng.jinGuBang = target.jinGuBang;
        qiTianDaSheng.birthday = new Date();
        return  qiTianDaSheng;
    }


}
