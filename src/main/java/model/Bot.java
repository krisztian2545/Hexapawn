package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.*;
import java.util.HashMap;
import java.util.List;

@Data
public class Bot extends Player {

    //private Map<String, List<Integer>> defMoves = new HashMap<>();
    private Brain brain;
    private final String importPath = "/brains/";

    private static Logger logger;

    public Bot(String name) {
        super(name);

        logger = LoggerFactory.getLogger(Brain.class);
        MDC.put("userId", "my user id");

        loadBrain();
    }

    public Bot(String name, boolean punish, boolean revard) {
        super(name);

        logger = LoggerFactory.getLogger(Brain.class);
        MDC.put("userId", "my user id");

        brain = new Brain(punish, revard, new HashMap<String, List<Integer>>());
    }


    private void loadBrain() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileReader fr = new FileReader(getFileName());

            brain = gson.fromJson(fr, Brain.class);

        } catch (FileNotFoundException e) {
            logger.error("File not found: " + e.getMessage());
            // create new brain
        }
    }

    private void exportBrain() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileWriter writer = new FileWriter(getFileName());
            gson.toJson(brain, writer);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getFileName() {
        //String filePath = ;
        return Bot.class.getResource( importPath + super.getName() + ".json" ).getPath();
    }

    public int getMove(String state) {
        return brain.process(state);
    }

    public void feedback(boolean won) {
        if(won)
            brain.revard();
        else
            brain.punish();
    }

}
