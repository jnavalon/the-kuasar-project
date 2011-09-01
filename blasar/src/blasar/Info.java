/*
 *  Copyright (C) 2010 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package blasar;

import java.util.Calendar;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */

public final class Info {
    private static boolean isVerbose(){
        return Config.BLASAR.verbose;
    }

    private static String[] getTime(){
        Calendar g = Calendar.getInstance();
         String[] date = new String[6];
            date[0] = Integer.toString(g.get(Calendar.YEAR));
            date[1] = Integer.toString(g.get(Calendar.MONTH));
            date[2] = Integer.toString(g.get(Calendar.DAY_OF_MONTH));
            date[3] = Integer.toString(g.get(Calendar.HOUR_OF_DAY));
            date[4] = Integer.toString(g.get(Calendar.MINUTE));
            date[5] = Integer.toString(g.get(Calendar.SECOND));
            return date;
    }
    public static void showError(String error){
        if(isVerbose()){
           String[] date = getTime();
            System.err.print("\n" + date[0]);
            for(int i=1 ; i< date.length; i++){
                if(date[i].length()<2){
                    date[i] = "0" + date[i];
                }
                System.err.print(date[i]);
            }
            System.err.println( "\n\t" + error);
        }
            
    }

    public static void showMessage(String message){
         if(isVerbose()){
           String[] date = getTime();
            System.out.print("\n" + date[0]);
            for(int i=1 ; i< date.length; i++){
                if(date[i].length()<2){
                    date[i] = "0" + date[i];
                }
                System.out.print(date[i]);
            }
            System.out.println("\t" + message);
        }
    }
}
