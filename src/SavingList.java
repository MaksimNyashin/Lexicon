import java.util.ArrayList;

class SavingList {
    private ArrayList<Game> list;
    private int sz;

    SavingList()
    {
        list = new ArrayList<>();
        sz = -1;
    }

    void addPlayer(String s)
    {
        if (sz == -1)
            list.add(new Game());
        else {
            list.add(new Game(list.get(sz)));
            if (list.get(sz).getSum() == 0) {
                list.remove(sz);
                sz--;
            }
        }
        sz++;
        list.get(sz).names.add(s);
        list.get(sz).score.add(0);
    }

    public void deletePlayer(Integer id) {
        if (sz == -1)
            return;
        list.add(new Game(list.get(sz), id));
        if (list.get(sz).getSum() == 0) {
            list.remove(sz);
            sz--;
        }
        sz++;
    }

    void newGame() {
        if (sz > -1 && list.get(sz).getSum() == 0) {
            list.remove(sz);
            sz--;
        }
        list.add(new Game());
        sz++;
    }

    public int getScore(int i) {
        return list.get(sz).score.get(i);
    }

    void plus(int i, boolean isDec) {
        try {
            if (isDec)
                list.get(sz).score.set(i, list.get(sz).score.get(i) - 1);
            else

                list.get(sz).score.set(i, list.get(sz).score.get(i) + 1);
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; i < list.size(); i++)
            ret.append("Game ").append(i).append(" ").append(list.get(i)).append("\n");
        return ret.toString();
    }

    private class Game {
        ArrayList<String> names;
        ArrayList<Integer> score;
        Game() {
            names = new ArrayList<>();
            score = new ArrayList<>();
        }
        Game(Game g) {
            names = new ArrayList<>();
            score = new ArrayList<>();
            for (String  i: g.names) {
                names.add(i);
                score.add(0);
            }
        }
        Game(Game g, int id) {
            names = new ArrayList<>();
            score = new ArrayList<>();
            if (g.names != null) {
                for (int i = 0; i < g.names.size(); i++) {
                    if (i == id)
                        continue;
                    try {
                        names.add(g.names.get(i));
                        score.add(0);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        int getSum() {
            int ans = 0;
            for (int i: score)
                ans += i;
            return ans;
        }

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder("\n");
            for (int i = 0; i < names.size(); i++)
                ret.append("    ").append(names.get(i)).append(": ").append(score.get(i)).append("\n");
            //ret.append("}");
            return ret.toString();
        }
    }
}
