package com.example.ljl.mygps;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljl on 2016/5/7 0007.
 */
public class Net1314080903234ReadXML {
//    private String cont;
    private List<String>cont;

    public List<String> getCont() {
        return cont;
    }

    public void setCont(List<String> cont) {
        this.cont = cont;
    }

    //    public String getCont() {
//        return cont;
//    }
//   public String getCont() {
//    return cont;
//}
//
//    public void setCont(String cont) {
//        this.cont = cont;
//    }
    public void ReadX(){
        String httpUrl = "https://github.com/soleMemory/ToLJianL/blob/master/ljl/test.xml";
        org.jsoup.nodes.Document doc = null;
        try {
//            <td id="LC1" class="blob-code blob-code-inner js-file-line">hello world</td>
            doc = Jsoup.connect(httpUrl).get();
//            Element masthead = doc.select("tbody").first();
//            Elements articleElements =  masthead.select("blob-code blob-code-inner js-file-line");
//          int j=    doc.getElementsByClass("blob-code blob-code-inner js-file-line").size();
            Element tbody =doc.select("tbody").first();
            int j=tbody.select("tr").size();
            List<String> list=new ArrayList<String>();

            for(int i = 1; i < j+1; i++) {
               String s="LC"+i;
                String txt =doc.getElementById(s).text();
                list.add(txt+"\n");

            }


                setCont(list);

//            cont= String.valueOf(j);



        } catch (IOException e) {
            e.printStackTrace();
        }

//        String s = "LC2";
//        cont =doc.getElementById(s).text();

    }
}
