/*
package fawda.risen;

import com.alibaba.fastjson.JSONArray;
import com.risen.base.encry.DesHexPwdCoder;
import com.risen.core.model.CoreDict;
import com.risen.oa.controller.Area;
import com.risen.oa.controller.City;
import com.risen.oa.controller.Proviance;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class initDict extends AppBaseTest {

    public static void main(String[] args) {
        DesHexPwdCoder desPwdEncoder = new DesHexPwdCoder();
        System.out.println(desPwdEncoder.decode("c4b8a4d0682337a3431d918c3d47fee3"));
        System.out.println(System.getProperties());
    }

    @Test
    //@Rollback(false)
    public void readProvinceAndCity_test() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/WebRoot/com/lcc/city_code.json");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
        int ch = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while ((ch = inputStreamReader.read()) != -1) {
            stringBuffer.append((char) ch);
        }
        inputStreamReader.close();

        List<Proviance> proviances = JSONArray.parseArray(stringBuffer.toString(), Proviance.class);
        for (Proviance proviance : proviances) {
            CoreDict coreDict = new CoreDict();
            coreDict.setCrdctParentUuid("EA1C7B82378C4B9A88AB1F06010C9BFE");
            coreDict.setCrdctName(proviance.getName());
            coreDict.setCrdctCode(proviance.getCode());
            coreDict.setCrdctType("2");
            this.ctx.coreDictService.save(coreDict);
            if (CollectionUtils.isEmpty(proviance.getCity())) {
                continue;
            }
            List<City> citys = proviance.getCity();
            for (City city : citys) {
                CoreDict dict = new CoreDict();
                dict.setCrdctParentUuid(coreDict.getUuid());
                dict.setCrdctName(city.getName());
                dict.setCrdctCode(city.getCode());
                dict.setCrdctType("2");
                this.ctx.coreDictService.save(dict);
                if (CollectionUtils.isEmpty(city.getArea())) {
                    continue;
                }
                List<Area> areas = city.getArea();
                for (Area area : areas) {
                    CoreDict cd = new CoreDict();
                    cd.setCrdctParentUuid(dict.getUuid());
                    cd.setCrdctName(area.getName());
                    cd.setCrdctCode(area.getCode());
                    cd.setCrdctType("2");
                    this.ctx.coreDictService.save(cd);
                }
            }
        }
        System.out.println(proviances.size());


    }
}
*/
