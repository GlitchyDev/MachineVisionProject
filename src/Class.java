public class Class {
    public static void main(String[] args) {
        boolean[][] input = new boolean[][]{new boolean[]{true, true, true, true, false, false, false}, new boolean[]{true, true, true, true, false, false, false}, new boolean[]{true, true, true, true, false, false, false}, new boolean[]{true, true, true, true, true, true, true}, new boolean[]{true, true, true, true, true, true, true}, new boolean[]{true, true, true, true, true, true, true}, new boolean[]{true, true, true, true, true, true, true}};
        boolean[][] cursor = new boolean[][] {new boolean[]{false, true, false}, new boolean[]{true, true, true}, new boolean[]{false, true, false},};

        boolean[][] storage = input;
        boolean[][] skeleton = new boolean[input.length][input[0].length];
        System.out.println("Input");
        displayGraph(input);
        System.out.println("Cursor");
        displayGraph(cursor);

        int step = 1;
        while(!isEmptyGraph(storage))
        {
            storage = erosion(storage,cursor);
            skeleton = mergeGraphs(skeleton,opening(storage,cursor));
            System.out.println("Run " + step);
            displayGraph(storage);
            step++;
        }
        System.out.println("Skeleton");
        displayGraph(skeleton);




    }

    public static boolean isEmptyGraph(boolean[][] graph)
    {
        for(int y = 0; y < graph[0].length; y++) {
            for (int x = 0; x < graph.length; x++) {
                if (graph[x][y])
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean[][] mergeGraphs(boolean[][] graph1, boolean[][] graph2)
    {
        boolean[][] result = new boolean[graph1.length][graph1[0].length];

        for(int y = 0; y < graph1[0].length; y++) {
            for (int x = 0; x < graph1.length; x++) {
                result[x][y] = graph1[x][y] || graph2[x][y];
            }
        }

        return result;
    }


    public static void displayGraph(boolean[][] graph)
    {
        for(int y = 0; y < graph[0].length; y++)
        {
            for(int x = 0; x < graph.length; x++)
            {
                if(graph[x][y])
                {
                    System.out.print("1");
                }
                else
                {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
        for(int i = 0; i < graph.length; i++)
        {
            System.out.print("-");
        }
        System.out.println();
    }

    public static boolean[][] opening(boolean[][] input, boolean[][] cursor)
    {
        return dilation(erosion(input,cursor),cursor);
    }


    public static boolean[][] closing(boolean[][] input, boolean[][] cursor)
    {
        return erosion(dilation(input,cursor),cursor);
    }

    public static boolean[][] dilation(boolean[][] input, boolean[][] cursor)
    {
        boolean[][] result = new boolean[input.length][input[0].length];

        for(int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++)
            {
                if(input[x][y])
                {
                    for(int x1 = -cursor.length/2; x1 <= cursor.length/2; x1++)
                    {
                        for(int y1 = -cursor[0].length/2; y1 <= cursor[0].length/2; y1++)
                        {
                            if(x+x1 >= 0 && x+x1 < input.length && y+y1 >= 0 && y+y1 < input[0].length)
                            {
                                result[x+x1][y+y1] = true;
                            }
                        }
                    }

                }
            }
        }
        return result;
    }

    public static boolean[][] erosion(boolean[][] input, boolean[][] cursor)
    {
        boolean[][] result = new boolean[input.length][input[0].length];
        int totalTrue = 0;
        for(int x = 0; x < cursor.length; x++)
        {
            for(int y = 0; y < cursor[0].length; y++)
            {
                if(cursor[x][y])
                {
                    totalTrue++;
                }
            }
        }

        // Move cursor and evaluate the opening over time
        for(int x = 0; x <= input.length - cursor.length; x++) {
            for (int y = 0; y <= input[0].length - cursor[0].length; y++)
            {
                int totalYes = 0;
                // Check the cursor points to see if there is a match
                for(int x1 = 0; x1 < cursor.length; x1++)
                {
                    for(int y1 = 0; y1 < cursor[0].length; y1++)
                    {
                        if(cursor[x1][y1] && input[x + x1][y + y1])
                        {
                            totalYes++;
                        }
                    }
                }
                // Evaluate if its "Saved"
                if(totalYes == totalTrue)
                {
                    result[x+cursor.length/2][y+cursor[0].length/2] = true;
                }
            }
        }
        return result;
    }

}
