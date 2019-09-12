package fawda.algorithm.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import fawda.algorithm.CoreApp;
import fawda.util.MyUtils;

public class CollectionsSort {
    public void testSortByMap() {
        List<String> codes = Arrays.asList("C1", "C2", "C3");
        List<CoreApp> coreApps = buildList();

        Map<String, CoreApp> map = MyUtils.CollectionToMap(coreApps, "uuid");

        List<CoreApp> sortList = new LinkedList<CoreApp>();
        for (String code : codes) {
            if (map.containsKey(code)) {
                sortList.add(map.get(code));
            }
        }

        Assert.assertEquals("C1", sortList.get(0).getUuid());
        Assert.assertEquals("C2", sortList.get(1).getUuid());
        Assert.assertEquals("C3", sortList.get(2).getUuid());

    }

    public void testSortByComparator() {
        final List<String> codes = Arrays.asList("C1", "C2", "C3");
        List<CoreApp> coreApps = buildList();
        Collections.sort(coreApps, new Comparator<CoreApp>() {
            @Override
            public int compare(CoreApp o1, CoreApp o2) {
                return codes.indexOf(o1.getUuid()) - codes.indexOf(o2.getUuid());
            }

        });

        Assert.assertEquals("C1", coreApps.get(0).getUuid());
        Assert.assertEquals("C2", coreApps.get(1).getUuid());
        Assert.assertEquals("C3", coreApps.get(2).getUuid());
    }

    public List<CoreApp> buildList() {
        List<CoreApp> coreApps = new LinkedList<CoreApp>();
        CoreApp coreApp1 = new CoreApp().lnkUuid("C1").lnkText("第一个");
        CoreApp coreApp2 = new CoreApp().lnkUuid("C2").lnkText("第二个");
        CoreApp coreApp3 = new CoreApp().lnkUuid("C3").lnkText("第三个");

        coreApps.add(coreApp2);
        coreApps.add(coreApp3);
        coreApps.add(coreApp1);
        return coreApps;
    }
}
