import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class practise {
    String[] arrOne;
    String[][] arrTwo;
    public static final String PATTERN_ARRAY_ONE = "(.*)\\$\\{_getData\\[\\d+]\\}(.*)";
    public static final String PATTERN_ARRAY_TWO = "(.*)\\$\\{_getData\\[\\d+]\\[\\d+]\\}(.*)";

    @BeforeClass
    public void Start() {
        System.out.println("=============================== START ============================");
        // mang 1 chieu,  length = 5
        getLengthArr(1,5,5);

        //mang 2 chieu row = 5, col = 3
        getLengthArr(2, 5, 3);
        addValueArray();

        System.out.println("========================== gia tri mang 1 chieu ======================");
        if (arrOne != null) {
            for (int i = 0; i < arrOne.length; i++) {
                System.out.println(arrOne[i]);
            }
        }
        System.out.println("========================== gia tri mang 2 chieu ======================");
        if (arrTwo != null) {
            for (int i = 0; i < arrTwo.length; i++) {
                for (int j=0;j<arrTwo[0].length;j++){
                    System.out.println(arrTwo[i][j]);
                }
            }
        }
    }


    //TC1 when value match arr1 pattern ${_getData[%d]}
    @Test
    public void TC1() {
        String result = (String)getValueToArray("${_getData[1]}", 1);
        Assert.assertEquals("test 1", result);
        System.out.println("Expected :test 1");
        System.out.println("Actual   :"+result);
    }

    //TC2 when value match arr2 pattern ${_getData[%d][%d]}
    @Test
    public void TC2() {
        String result = (String)getValueToArray("${_getData[1][2]}", 2);
        Assert.assertEquals("row 1 col 2",result);
        System.out.println("Expected :row 1 col 2");
        System.out.println("Actual   :"+result);
    }
    //TC3 when value not match arr1  pattern ${_getData[%d]}
    @Test
    public void TC3() {
        boolean result = (Boolean)getValueToArray("TEST$Data[1]}", 1);
        Assert.assertFalse(result);
        System.out.println("Expected :false");
        System.out.println("Actual   :"+result);
    }
    //TC4 when value not match arr2 pattern ${_getData[%d][%d]}
    @Test
    public void TC4() {
        boolean result = (Boolean)getValueToArray("TEST$Data[1][2]}", 2);
        Assert.assertFalse(result);
        System.out.println("Expected :false");
        System.out.println("Actual   :"+result);
    }
    //TC5 when value have index > arr2 pattern ${_getData[%d][%d]}
    @Test
    public void TC5() {
        String result = (String)getValueToArray("${_getData[1][10]}", 2);
        Assert.assertEquals("Invalid array row 10", result);
        System.out.println("Expected :Invalid array row 10");
        System.out.println("Actual   :"+ result);
    }
    //TC5 when value have index > arr1 pattern ${_getData[%d][%d]}
    @Test
    public void TC6() {
        String result = (String)getValueToArray("${_getData[7]}", 1);
        Assert.assertEquals("Invalid array index 7", result);
        System.out.println("Expected :Invalid array index 7");
        System.out.println("Actual   :Invalid array index 7");
    }
    //TC7 when value have String contains arrOne pattern ${_getData[%d]}
    @Test
    public void TC7() {
        String result = (String)getValueToArray("thang${_getData[1]}TestNG", 1);
        Assert.assertEquals("test 1", result);
        System.out.println("Expected :test 1");
        System.out.println("Actual   :" + result);
    }
    //TC8 when value have String contains arrTwo pattern ${_getData[%d]}
    @Test
    public void TC8() {
        String result = (String)getValueToArray("thang${_getData[1][2]}TestNG", 2);
        Assert.assertEquals("row 1 col 2", result);
        System.out.println("Expected :row 1 col 2");
        System.out.println("Actual   :" + result);
    }
    //TC9 when value have 2 index in arrOne pattern ${_getData[%d]}
    @Test
    public void TC9() {
        Boolean result = (Boolean)getValueToArray("thang${_getData[1][2]}TestNG", 1);
        Assert.assertFalse(result);
        System.out.println("Expected :false");
        System.out.println("Actual   :" + result);
    }
    //TC10 when value have 1 index in arrOne pattern ${_getData[%d][%d]}
    @Test
    public void TC10() {
        Boolean result = (Boolean)getValueToArray("thang${_getData[1]}TestNG", 2);
        Assert.assertFalse(result);
        System.out.println("Expected :false");
        System.out.println("Actual   :" + result);
    }

    @AfterClass
    public void TearDown() {
        System.out.println("===================================== END TESING =================================");
    }

    public void getLengthArr(int a, int row, int col) {
        if (a == 1) {
            arrOne = new String[row];
        } else if (a == 2) {
            arrTwo = new String[row][col];
        } else {
            arrOne = null;
            arrTwo = null;
        }
    }

    public void addValueArray() {
        if (arrOne != null) {
            for (int i = 0; i < arrOne.length; i++) {
                arrOne[i] = "test " + i;
            }
        }  if (arrTwo != null) {
            for (int i = 0; i < arrTwo.length; i++) {
                for (int j = 0; j < arrTwo[0].length; j++) {
                    arrTwo[i][j] = "row " + i + " col " + j;
                }
            }
        }
    }

    public Object getValueToArray(String data, int number) {
        Pattern pattern = null;
        Object obj = null;
        String index = "";
        if (number == 1) {
            pattern = Pattern.compile(PATTERN_ARRAY_ONE);
            if (pattern.matcher(data).matches()) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(data);
                while (m.find()) {
                    index = index + m.group() + ",";
                }
                String[] arr = index.split(",");
                String message = checkLengthArr(1,Integer.parseInt(arr[0]),0);
                if(!message.equals("")){
                    return message;
                }else{
                    return arrOne[Integer.parseInt(arr[0])];
                }

            }else{
                return  false;
            }


        } else if (number == 2) {
            pattern = Pattern.compile(PATTERN_ARRAY_TWO);
            if (pattern.matcher(data).matches()) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(data);
                while (m.find()) {
                    index = index + m.group() + ",";
                }
                String[] arr = index.split(",");
                String message = checkLengthArr(2,Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));
                if(!message.equals("")){
                    return message;
                }else{
                    return arrTwo[Integer.parseInt(arr[0])][Integer.parseInt(arr[1])];
                }

            }
            else{
                return  false;
            }
        }
        return obj;
    }
    public String checkLengthArr(int number, int col, int row){
        if(number==1){
            if(arrOne.length<col){
                return "Invalid array index "+ col;
            }
        }
        if(number==2){
            if(arrTwo.length<row){
                return "Invalid array row "+ row;
            }
            else if(arrTwo[0].length<col){
                return "Invalid array col "+ col;
            }
        }
        return "";

    }

    public static void main(String[] args) {
//        String strTest = "Regex 15-05-2020, Nguyen Minh Duc 16/02/1998. Deadline 18_02_2020";
//        Pattern patternDate = Pattern.compile("\\d{2}[-|/]\\d{2}[-|/]\\d{4}");
//        Matcher matcher = patternDate.matcher(strTest);
//        System.out.println(matcher.matches());
//        System.out.println("Ng??y th??ng n??m trong chu???i ?????u v??o: " + strTest +" l??:");
//        while(matcher.find()) {
//            System.out.println(strTest.substring(matcher.start(), matcher.end()));
//        }

//        String USERNAME_PATTERN = "^[a-z0-9._-]{3,15}$";
        String USERNAME_PATTERN = "\\$\\{_getData\\[\\d{1}]\\[\\d{1}]\\}";
//        String USERNAME_PATTERN = "\\$\\{_getData\\[\\d{1}]\\}";
        String test = "${_getData[1][2]}";
        String index = "";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        boolean matches = pattern.matcher(test).matches();
        System.out.println("pattern.matcher(test).start()==" + pattern.matcher(test));
        System.out.println(matches);
        if (matches) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(test);
            while (m.find()) {
                System.out.println("===" + m.group());
                index = index + m.group() + ",";
            }
            System.out.println("index ==" + index);

        }

    }

}
