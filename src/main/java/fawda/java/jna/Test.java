// package fawda.java.jna;
//
// import com.sun.jna.Library;
// import com.sun.jna.Native;
// import com.sun.jna.Platform;
// import org.hsqldb.Library;
//
// import java.lang.annotation.Native;
//
// /**
//  * <b>时间:</b> <i>2019-05-13 16:35</i> 修订原因:初始化创建.详细说明:<br>
//  * <br>
//  * Action类{@linkplain lcc.liu.java.jna}使用<br>
//  *
//  * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
//  *
//  * @author Fawda liuyl@hzrisencn.com @since 1.0
//  **/
// public class Test {
//     public interface CLibrary extends Library {
//         CLibrary INSTANCE = (CLibrary)
//                 Native.load((Platform.isWindows() ? "msvcrt" : "c"),
//                         CLibrary.class);
//
//         void printf(String format, Object... args);
//     }
//
//     public static void main(String[] args) {
//         CLibrary.INSTANCE.printf("Hello, World\n");
//         for (int i=0;i < args.length;i++) {
//             CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
//         }
//     }
// }
