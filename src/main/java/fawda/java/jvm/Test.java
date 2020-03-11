package fawda.java.jvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String appPath = System.getProperty("user.dir").trim().replaceAll("\\\\", "/");
        String filePath = "/com.lcc.liu/WebRoot/com/lcc/jvm/thread2.txt";
        Pattern p = Pattern.compile("at com.risen");
        File file = new File(appPath + filePath);
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new FileReader(file));
            String tem = null;
            Map<String, Integer> map = new HashMap<String, Integer>();
            Map<String, String> map2 = new HashMap<String, String>();
            while ((tem = bfr.readLine()) != null) {
                String str = tem.trim();
                Matcher m = p.matcher(str);
                if (m.matches()) {
                    String key = str.substring(str.indexOf("com"), str.lastIndexOf("("));
                    if (map.containsKey(key)) {
                        Integer integer = map.get(key);
                        map.put(key, ++integer);
                    } else {
                        map.put(key, 0);
                    }
                    map2.put(key, str);
                }
            }
            
            List<Map.Entry<String,Integer>> entrySet = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
            Collections.sort(entrySet, new Comparator<Map.Entry<String,Integer>>(){
                @Override
                public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            
            for (Iterator<Map.Entry<String,Integer>> it = entrySet.iterator(); it.hasNext(); ) {
                Entry<String, Integer> next = it.next();
                System.out.println(next.getValue() + "    |    " + map2.get(next.getKey()));
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
