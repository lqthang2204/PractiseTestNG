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
    public static final String PATTERN_ARRAY_ONE = "\\$\\{_getData\\[\\d{1}]\\}";
    public static final String PATTERN_ARRAY_TWO = "\\$\\{_getData\\[\\d{1}]\\[\\d{1}]\\}";

    @BeforeClass
    public void Start() {
        System.out.println("=============================== START ============================");
        // mang 1 chieu,  length = 5
        getLengthArr(1,5,5);

        //mang 2 chieu row = 5, col = 2
        getLengthArr(2, 5, 3);
        addValueArray();
    }

    @BeforeClass
    public void printArr() {
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

    //TC1 when value match pattern ${_getData[%d]}
    @Test
    public void TC1() {
        String result = getValueToArray("${_getData[1]}", 1);
        Assert.assertEquals("test 1", result);
        System.out.println("TC1 is Successful");
    }

    //TC1 when value match pattern ${_getData[%d][%d]}
    @Test
    public void TC2() {
        String result = getValueToArray("${_getData[1][2]}", 2);
        Assert.assertEquals("row 1 col 2",result);
        System.out.println("TC2 is Successful");
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

    public String getValueToArray(String data, int number) {
        Pattern pattern = null;
        if (number == 1) {
            pattern = Pattern.compile(PATTERN_ARRAY_ONE);
            if (pattern.matcher(data).matches()) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(data);
                while (m.find()) {
                    System.out.println("====== " + m.group());
                    return arrOne[Integer.parseInt(m.group())];
                }

            }


        } else if (number == 2) {
            pattern = Pattern.compile(PATTERN_ARRAY_TWO);
            String index = "";
            if (pattern.matcher(data).matches()) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(data);
                while (m.find()) {
                    index = index + m.group() + ",";
                }
                String[] arr = index.split(",");
                return arrTwo[Integer.parseInt(arr[0])][Integer.parseInt(arr[1])];
            }
        }
        return "";
    }

    public static void main(String[] args) {
//        String strTest = "Regex 15-05-2020, Nguyen Minh Duc 16/02/1998. Deadline 18_02_2020";
//        Pattern patternDate = Pattern.compile("\\d{2}[-|/]\\d{2}[-|/]\\d{4}");
//        Matcher matcher = patternDate.matcher(strTest);
//        System.out.println(matcher.matches());
//        System.out.println("Ngày tháng năm trong chuỗi đầu vào: " + strTest +" là:");
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
