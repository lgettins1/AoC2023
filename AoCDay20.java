import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class AoCDay20 {
    static class Module {
        String type; String[] targets; String[] states;
        ArrayList<String> inputList;
        public Module(String _type, String[] _targets, ArrayList<String> _inputList,String[] _states){
            type = _type; targets = _targets; inputList = _inputList; states = _states;
        }
    }
    public static void main(String[] args) {

        HashMap<String,Module> moduleMap = new LinkedHashMap<>();
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day20input.txt"));
            while ((thisLine = br.readLine()) != null) {
                String[] a = thisLine.split(">");
                String[] b = a[0].split(" ");
                String[] targets = a[1].split(",");
                String mod;
                String type = "bCast";
                if (b[0].equals("broadcaster")) {
                    mod = b[0];
                } else {
                    mod = b[0].substring(1);
                    if (b[0].charAt(0) == '%') {
                        type = "flip";
                    } else {
                        type = "con";
                    }
                }
                ArrayList<String> il = new ArrayList<>();
                String[] is = new String[20];
                moduleMap.put(mod, new Module(type, targets, il, is));
            }
            ArrayList<String> keys = new ArrayList<>(moduleMap.keySet());
            for (String key : keys) {
                int tc = moduleMap.get(key).targets.length;
                String[] ts = moduleMap.get(key).targets;
                for (int b = 0; b < tc; b++) {
                    Module tgt = moduleMap.get(ts[b].trim());
                    if(!(tgt == null)){
                        if(tgt.type.equals("con")) {
                            tgt.inputList.add(key);
                            tgt.states[tgt.inputList.size() - 1] = "low";
                        }
                    }
                }
                if(moduleMap.get(key).type.equals("flip")) moduleMap.get(key).states[0] = "off";
            }
            ArrayList<String[]> steps = new ArrayList<>();
            int lowCount = 0;
            int highCount = 0;

            for(int buttonPushes = 0; buttonPushes < 1000; buttonPushes ++) {
                for (String key : keys) {
                     System.out.print(key + " " +moduleMap.get(key).type + " ");
                     if(moduleMap.get(key).type.equals("con")) {
                         for (int c = 0; c < moduleMap.get(key).inputList.size(); c++) {
                             System.out.print(moduleMap.get(key).states[c] + " ");
                         }
                     } else {
                         System.out.print(moduleMap.get(key).states[0]);
                     }
                     System.out.println();
                }
                System.out.println();
                steps.add(new String[]{"button","broadcaster", "low"});
                lowCount++;
                while(steps.size() > 0){
                    String[] readStep = steps.get(0);
                    String mod = readStep[1].trim();
                    String pulse = readStep[2].trim();
                    Module modInfo = moduleMap.get(mod);
                    if(modInfo == null) {
                        steps.remove(0);
                        continue;
                    }
                    String[] tg = modInfo.targets;
                    String[] nextStep = new String[] {mod, "x", "low"};
                    String fState = modInfo.states[0];

                    switch(modInfo.type){
                        case "bCast":
                            for (String s : tg) {
                                lowCount++;
                                String a = nextStep[0];
                                String c = nextStep[2];
                                String[] newStep = {a,s,c};
                                steps.add(newStep);
                            }
                            break;
                        case "flip":
                            if(!(pulse.equals("high"))){
                                if(fState.equals("off")){
                                    nextStep[2] = "high";
                                    highCount += tg.length;
                                } else {
                                    nextStep[2] = "low";
                                    lowCount += tg.length;
                                }
                                for (String s : tg) {
                                    String a = nextStep[0];
                                    String c = nextStep[2];
                                    String[] newStep = {a,s,c};
                                    steps.add(newStep);
                                }
                                if(modInfo.states[0].equals("off")){
                                    modInfo.states[0] = "on";
                                }else{
                                    modInfo.states[0] = "off";
                                }
                            }
                            break;
                        case "con":
                            for(int ipCount = 0; ipCount < modInfo.inputList.size(); ipCount ++){
                                if(modInfo.inputList.get(ipCount).equals(readStep[0])) {
                                    modInfo.states[ipCount] = pulse;
                                }
                            }
                            String op = "low";
                            for(int ipCount = 0; ipCount < modInfo.inputList.size(); ipCount ++){
                                if (modInfo.states[ipCount].equals("low")) {
                                   op = "high";
                                   break;
                                }
                            }
                            if(op.equals("low")){
                                lowCount += tg.length;
                            } else {
                                highCount += tg.length;
                            }
                           for (String s : tg) {
                                String a = nextStep[0];
                                String[] newStep = {a,s,op};
                                steps.add(newStep);
                            }
                    }
                    steps.remove(0);
                }
            }
            System.out.println("The answer is " + lowCount + " * " + highCount + " = " + (lowCount * highCount));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
