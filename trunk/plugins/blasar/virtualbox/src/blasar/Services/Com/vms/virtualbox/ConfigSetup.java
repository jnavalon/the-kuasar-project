/*
 * Copyright (C) 2011 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package blasar.Services.Com.vms.virtualbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class ConfigSetup {

    private String emptyStr = null;
    public boolean startConfig() {
        int option;
        String app = null;
        String vmdir = null;
        printMainMenu();
        option = getOption();
        switch (option) {
            case 1:
                app = getVMBPath();
                break;
            case 2:
                app = searchVMB();
                break;
            default:
                return false;
        }
        if (app == null) {
            return false;
        }
        vmdir=getVMIPath();
        if(vmdir ==null)
            return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(Config.startDir + File.separator
                    + Config.pluginName.toLowerCase() + ".cfg"));
            bw.write("app=" + app + "\n");
            bw.write("vmipath=" + vmdir + "\n");
            bw.flush();
        } catch (IOException ex) {
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ex) {
                }

            }
        }
        return true;
    }

    private int getOption() {
        String command;
        int option;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            command = br.readLine();
        } catch (IOException ex) {
            return -1;
        }

        try {
            option = Integer.parseInt(command);
            return option;
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }
    
    private char getAnswer(){
        String command;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            command = br.readLine();
        } catch (IOException ex) {
            return 0;
        }
        if(command.length()!=1 ) return 0;
        return command.charAt(0);
    }

    private String getPath() {
        String command = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            command = br.readLine();
        } catch (IOException ex) {
            return null;
        }
        return command;
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("***************************************************");
        System.out.println("       " + Config.pluginName + " configuration menu       ");
        System.out.println("***************************************************");
        System.out.println();
        System.out.println("1) Insert manually VBoxManage path");
        System.out.println("2) Search automatically");
        System.out.println();
        System.out.print("Type your option [1,2] : ");
    }
    
    private String getVMIPath() {
        boolean cont = false;
        int tries = 0;
        String path;
        System.out.println("We need too the path when we save the VM images disk.");
        do {
            path = null;
            System.out.println();
            System.out.println();
            System.out.print("VBox Image Dir: ");
            path = getPath();
            File file = new File(path);
            if (file.exists()) {
                if (file.isFile()) {
                    System.err.println("That was a file! Tries: " + (2 - tries));
                    path=null;
                } else {
                    if (file.canWrite()) {
                        cont = true;
                    } else {
                        System.err.println("Directory can't be writed! Tries: " + (2 - tries));
                        path=null;
                    }
                }
            } else {
                System.out.print("Directory not found! Do you want create it? [y/n]: ");
                switch(getAnswer()){
                    case 'y':
                        if(file.mkdirs()) System.out.println("Directory was created successfully!");
                        else System.out.println("Ther was an error while was creating directory!");
                        path=file.getAbsolutePath();
                        cont = true;
                        break;
                    default:
                        System.out.println("Directory wasn't created!");
                    case 'n': 
                        path=null;
                        break;
                }
            }
            tries++;
        } while (cont == false && tries <= 2);
        return path;
    }

    private String getVMBPath() {
        boolean cont = false;
        int tries = 0;
        String path;
        do {
            path = null;
            System.out.println();
            System.out.println();
            System.out.print("VBox: ");
            path = getPath();
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    System.err.println("That was a directory! Tries: " + (2 - tries));
                    path=null;
                } else {
                    if (file.canExecute()) {
                        cont = true;
                    } else {
                        path=null;
                        System.err.println("File can't be executed! Tries: " + (2 - tries));
                    }
                }
            } else {
                path=null;
                System.err.println("File not found! Tries: " + (2 - tries));
            }
            tries++;
        } while (cont == false && tries <= 2);
        return path;
    }

    private String searchVMB() {
        ArrayList<String> apps = search();
        if(apps.isEmpty()){
            System.out.println("No application found on disk!");
            return null;
        }
        printResults(apps);
        int option = getOption();
        if(option<0 || option>apps.size()-1){
            System.out.println("Application no saved!");
            return null;
        }
        return apps.get(option);
    }

    private ArrayList<String> search() {
        for(int i=0; i<60;i++){
            emptyStr+=" ";
        }
        ArrayList<String> files = new ArrayList<String>();
        String os = System.getProperty("os.name").toLowerCase();
        String wSearch = Config.VMAPP;
        boolean estricted = true;
        boolean forbDir = true;
        if(os.indexOf("win")>=0){
            wSearch = Config.VMAPP +".exe";
            forbDir = false;
        }if(os.indexOf("mac")>=0 || os.indexOf("nix")>=0){
            //already preconfigured
        }else{
            estricted =false;
        }
        File[] roots = File.listRoots();
        System.out.println();
        System.out.println("Searching... ");
        for(File root : roots){
            recursiveSearch(wSearch, root, files, estricted, forbDir);
        }
        System.out.println("Finished! ");
        System.gc();
        return files;
    }
    
    private void recursiveSearch(String filename ,File dir, ArrayList<String> data, boolean estricted, boolean forbDir){
        if(isForbidden(dir)) return;
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                printDir(file.getAbsolutePath());
                if(file.canRead())
                        recursiveSearch(filename,file,data,estricted, forbDir);
            }else{
                if((estricted?file.getName().equals(filename):file.getName().contains(filename))){
                    if(file.canExecute()){
                        data.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private void printResults(ArrayList<String> apps) {
        System.out.println();
        System.out.println("Apps found: ");
        for(int i=0; i<apps.size(); i++){
            System.out.println(i + ") " + apps.get(i));
        }
        System.out.println();
        System.out.print("Select the application [0-" + (apps.size() - 1) + "] : ");
    }

    private boolean isForbidden(File dir) {
        if(dir.getAbsolutePath().startsWith("/sys"))
            return true;
        if(dir.getAbsolutePath().startsWith("/proc"))
            return true;
        return false;
    }

    private void printDir(String path) {
        System.out.print('\r'+emptyStr);
        String info ="";
        if(path.length()>60){
            info = path.substring(0, 15);
            info += "[...]";
            info += path.substring(path.length()-40, path.length());
        }else{
            info=path;
        }
        System.out.print('\r'+info);
    }
    
}
