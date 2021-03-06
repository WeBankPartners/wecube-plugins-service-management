package com.webank.servicemanagement.utils;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import com.google.common.collect.Lists;

public class JsonUtilsTest {

    @Test
    public void toStringTest() throws Exception {
        List<String> list = Lists.newArrayList("deny", "approval", "return");
        String str = JsonUtils.toJsonString(list);

        System.out.println("list= " + list);
        System.out.println("str=" + str);

        List<String> list2 = new ArrayList<String>();

        list2 = JsonUtils.toObject(str, list.getClass());
        System.out.println("list2= " + list2);

        assertThat(list).isEqualTo(list2);
    }

}
