package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot extends Player {

    private Map<String, List<Integer>> defMoves = new HashMap<>();

    public Bot() {
        defMoves.put("123759", new ArrayList<Integer>() {{
            add(2);
            add(3);
        }});
        defMoves.put("123489", new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(8);
        }});
        defMoves.put("423756", new ArrayList<Integer>() {{
            add(6);
            add(7);
        }});
        defMoves.put("523706", new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(6);
        }});
        defMoves.put("120469", new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(6);
        }});
        defMoves.put("103049", new ArrayList<Integer>() {{
            add(8);
        }});
        defMoves.put("126459", new ArrayList<Integer>() {{
            add(3);
            add(4);
        }});
        defMoves.put("103485", new ArrayList<Integer>() {{
            add(3);
            add(7);
            add(8);
        }});
        defMoves.put("523409", new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(4);
            add(8);
        }});
        defMoves.put("023509", new ArrayList<Integer>() {{
            add(7);
            add(8);
        }});
        defMoves.put("143059", new ArrayList<Integer>() {{
            add(3);
            add(5);
            add(7);
            add(8);
        }});
        defMoves.put("143086", new ArrayList<Integer>() {{
            add(5);
            add(6);
        }});
        defMoves.put("023705", new ArrayList<Integer>() {{
            add(7);
            add(8);
        }});
        defMoves.put("420506", new ArrayList<Integer>() {{
            add(2);
            add(6);
        }});
        defMoves.put("140005", new ArrayList<Integer>() {{
            add(3);
            add(5);
        }});
        defMoves.put("145006", new ArrayList<Integer>() {{
            add(5);
            add(8);
        }});
        defMoves.put("100465", new ArrayList<Integer>() {{
            add(3);
        }});
        defMoves.put("563400", new ArrayList<Integer>() {{
            add(2);
            add(5);
        }});
        defMoves.put("520400", new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(8);
        }});
    }

}
