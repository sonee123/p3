package rightchamps;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTestClass {
    public static void main(String args[]) throws java.text.ParseException{
        System.out.println(parse("2018-10-18T10:04:28.311+0000"));
    }
    public static Date parse(String input ) throws java.text.ParseException {

        //NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
        //things a bit.  Before we go on we have to repair this.
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm a z" );

        return df.parse( input );

    }
}
