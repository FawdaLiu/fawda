定义：　持有对象的一个引用
1、为其他对象提供一种代理，以控制对这个对象的访问
2、代理对象在客户端和目标对象之间起到中介作用
3、属于结构型设计模式

有点：
    1、代理模式能将代理对象与真实被调用的目标对象分离
    2、一定程度上降低了系统的耦合程度，易于扩展
    3、代理起到了保护目标对象的作用
    4、增强目标对象的职责

缺点：
    1、代理模式会造成系统设计中类的数目增加
    2、在客户端和目标对象之间增加了一个代理对象，会造成请求处理速度变慢
    3、增加了系统的复杂度


静态代理：显示声明代理对象，不符合开闭原则,类是固定的, 代理对象的方法改变时,被代理对象也要跟着改变

动态代理：在运行时动态生成代码的方式，取消了被代理类的限制。
    1、ｊｄｋ
        原理：
            a、拿到被代理类的引用，并且获取它的所有的接口（反射获取）
            b、JDK Proxy类重新生成一个新的类，实现了被代理类所有接口的方法。
            c、动态生成java代码，把增强逻辑加入到新生成代码中。
            d、编译生成新的java代码的class文件。
            e、加载并重新运行新的class，得到的类就是全新的
    ２、ＣＧＬＢ
    ３、阿里


思考：为什么JDK动态代理中要求目标类实现的接口数量不能超过65535个？
EXP:
下面为隐含在Class文件格式中的java虚拟机限制：

1、每个类活接口的常量池最多为65535个，它是由ClassFile结构中的16位const_pool_count字段的值决定，这限制了单个类或接口的复杂度；
2、方法调用时创建的栈帧的局部变量表中的最大局部变量个数65535个，它是由方法代码所处的Code属性中的maxx_locals项的值和java虚拟机指令集的16位局部变量索引决定。注意，每个long和double类型都被认为会使用两个局部变量位置并占据max_locals中的两个单元，所以使用这些类型时局部变量的限制的最大值就会相应㝶减少；
3、类或接口中可以声明的字段数最多为65535个，它是由ClassFile结构中的fields_count项的值决定的，注意ClassFile结构中的fields_count项的值不包含从父类或父接口中继承下来的字段；
4、类或接口中可以声明的方法数最多为65535个，它是由ClassFile结构中的methods_count项的值所决定。注意，ClassFile结构中的methods_count项的值不包含从父类或父接口中继承下来的方法；
5、类或接口的直接父接口数量最多为65535个，它是由ClassFile结构中的interfaces_count项的值所决定的；
6、方法栈中的操作数栈的大小深度为65535，它是由Code属性的max_stack字段值来决定。需要注意的是每个long和double类型的数据被认为占用max_locals中的两个单元，所以使用这些类型时，操作数栈的限制的最大值就会相应的减少；
7、数组的维度最大为255维，这由multianewarray指令的dimensions操作码及multianewarray,anewarry和newarray指令的约束来决定；
8、方法的参数最多有255个，它是由方法描述符的定义所限制，如果方法调用是针对实例或接口方法，那么这个限制也包含着占有一个单元的this。注意对于定义在方法描述符中的参数长度来说，每个long和double参数都会占用两个长度单位，所以如果有这些类型的话，最终的限制的最大值将会变小；
9、字段和方法名称，字段和方法描述符以及其他常量字符串值（由ConstantValue属性引用的值）最大长度为65535个字符，它是由CONSTANT_Utf8_info结构的16位无符号length项决定。需要注意的是，这里的限制是已编码字符串的字节数量而不是被编码的字符数量。UTF-8一般用两个或三个字节来编码字符，因此，当字符串中包含多字节字符时，会受到更大的约束。


Q： 这个是Java 虚拟机规范规定的。
    首先要知道Class类文件结构：
       * Class文件是一组以8字节为基础单位的二进制流，
       * 各个数据项目严格按照顺序紧凑排列在class文件中，
       * 中间没有任何分隔符，这使得class文件中存储的内容几乎是全部程序运行的程序。

    ClassFile 结构体，如下：
       ClassFile {
           u4             magic;
           u2             minor_version;
           u2             major_version;
           u2             constant_pool_count;
           cp_info        constant_pool[constant_pool_count-1];
           u2             access_flags;
           u2             this_class;
           u2             super_class;
           u2             interfaces_count;
           u2             interfaces[interfaces_count];
           u2             fields_count;
           field_info     fields[fields_count];
           u2             methods_count;
           method_info    methods[methods_count];
           u2             attributes_count;
           attribute_info attributes[attributes_count];
       }

     各项的含义描述：
     1，无符号数，以u1、u2、u4、u8分别代表1个字节、2个字节、4个字节、8个字节的无符号数
     2，表是由多个无符号数或者其它表作为数据项构成的复合数据类型，所以表都以“_info”结尾，由多个无符号数或其它表构成的复合数据类型

    看上边第18行 interfaces_count 接口计数器，interfaces_count 的值表示当前类或接口的直接父接口数量。类型是u2， 2个字节，即 2^16-1 = 65536-1 = 65535
所以目标类实现的接口数量不能超过65535个