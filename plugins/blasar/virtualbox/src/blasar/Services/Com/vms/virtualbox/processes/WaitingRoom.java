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
package blasar.Services.Com.vms.virtualbox.processes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class WaitingRoom {

    private ScheduledExecutorService scheduler;

    public boolean waitForRun(String cmd, int timeRep, int repetitions) {
        scheduler = Executors.newScheduledThreadPool(1);
        Runner runner = new Runner(cmd, repetitions);
        scheduler.scheduleWithFixedDelay(runner, 0, timeRep, TimeUnit.SECONDS);
        try {
            scheduler.awaitTermination(timeRep * repetitions + 20, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {}
        return runner.getStatus();
    }
    
    public boolean waitForShutdown(String uuid, int timeRep, int repetitions){
        scheduler = Executors.newScheduledThreadPool(1);
        Shutdown shut = new Shutdown(uuid, repetitions);
        scheduler.scheduleWithFixedDelay(shut, timeRep, timeRep, TimeUnit.SECONDS);
        try {
            scheduler.awaitTermination(timeRep * repetitions + 5, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {}
        return shut.getStatus();
    }
    
    class Shutdown implements Runnable{
        private String uuid;
        private int repetitions, count =0;
        private boolean ok = false;
        Shutdown(String uuid, int repetitions){
            this.uuid = uuid;
            this.repetitions = repetitions;
        }
        @Override
        public void run() {
            if(!Machines.isRunning(uuid)){
                ok = true;
                scheduler.shutdown();
            }
            if(count == repetitions){
                scheduler.shutdown();
            }
            count++;
        }
        public boolean getStatus(){
            return ok;
        }
    }

    class Runner implements Runnable {
        private boolean ok;
        private String cmd;
        private int repetitions, count =0;

        Runner(String cmd, int repetitions) {
            this.cmd = cmd;
            this.repetitions = repetitions;
        }

        @Override
        public void run() {
            if(Hypervisor.execute(cmd)==0){
                ok = true;
                scheduler.shutdown();
            }
            if(count==repetitions){
                scheduler.shutdown();
            }
            count++;
        }
        public boolean getStatus(){
            return ok;
        }
    }
}
