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

/**
 * Extends the Player class with an importable/exportable Brain,
 * goes in even number of rounds.
 */
@Data
public class Bot extends Player {

    private Brain brain;
    private final String importPath = "brains/";

    private static Logger logger;

    /**
     * sets the name and loads the brain
     * @param name - the name of the enemy and also the of the file containing his Brain
     */
    public Bot(String name) {
        super(name);

        logger = LoggerFactory.getLogger(Brain.class);
        MDC.put("userId", "my user id");

        loadBrain();
        brain.initLogger();

        logger.debug("The loaded brains moves: {}", brain.getPossibleMoves());
    }

    /**
     * sets the name and creates a new brain with the given parameters
     * @param name - the name of the enemy and also the of the file containing his Brain
     */
    public Bot(String name, boolean punish, boolean revard) {
        super(name);

        logger = LoggerFactory.getLogger(Brain.class);
        MDC.put("userId", "my user id");

        brain = new Brain(punish, revard, new HashMap<String, List<Integer>>());
        brain.initLogger();
    }


    /**
     * loads the brain from the '/brains' folder from the .json with the same name as
     */
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

    public void exportBrain() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            File f = new File(getFileName());
            logger.info("Checking file ({}) if exsists...", f.getPath());
            if(!f.exists()) {
                f.createNewFile();
                logger.info("Creating new file...");
            }

            FileWriter writer = new FileWriter(getFileName());
            logger.debug("Filewriter: ");
            gson.toJson(brain, writer);
            writer.close();
            logger.debug("brain: {}", gson.toJson(brain));
        } catch (IOException e) {
            logger.error("In exportBrain: {}", e.getMessage());
        }
    }

    private String getFileName() {
        String filePath = "";
        //try {
            filePath = /*Bot.class.getResource( importPath ).getPath() +*/ importPath + super.getName() + ".json";
        /*} catch (NullPointerException e) {
            logger.info("File doesn't exist...");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try {
                FileWriter writer = new FileWriter(getFileName());
                gson.toJson(brain, writer);
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }*/


        return filePath;
    }

    public int getMove(String state) {
        return brain.process(state);
    }

    public void feedback(boolean won) {
        brain.initLogger();
        if(won)
            brain.revard();
        else
            brain.punish();

        exportBrain();
    }

}
