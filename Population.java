import java.util.Random;

public class Population
{
    public MyLinkedList<Genome> population;
    public Genome mostFit;
    public int generation;
    private final int size = 100;

    public static void main(String[] args)
    {
        Population population = new Population();

        System.out.println("Evolving names...");

        long start = System.currentTimeMillis();

        while (population.mostFit.fitness() < 0) 
        {
            population.nextGeneration();
            System.out.println(population);
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println("\nEvolving names took " + duration + " milliseconds.\n");
        
    }

    public Population()
    {
        population = new MyLinkedList<>();
        generation = 0;

        for(int i = 0; i < size; i++)
        {
            population.add(new Genome());
        }
        mostFit = population.getFirst();
    }
    public void nextGeneration()
    {
        population.first();
        for(int i = 0; i < 50; i++)
        {
            population.remove();
        }
        Genome[] fitParents = new Genome[50];
        int i = 0;
        while(population.current() != null)
        {
            fitParents[i] = population.current();
            i++;
            population.next();
        }

        Random rnd = new Random();
        while(population.size() < 100)
        {
            int coinFlip = rnd.nextInt(2);
            if(coinFlip == 0)
            {
                Genome child = new Genome(fitParents[rnd.nextInt(0, 50)]);
                child.mutate();
                population.add(child);
            }
            else
            {
                Genome parent = fitParents[rnd.nextInt(0, 50)];
                Genome child = new Genome(fitParents[rnd.nextInt(0, 50)]);
                child.crossover(parent);

                child.mutate();
                population.add(child);
            }
        }
        population.sort();
        mostFit = population.getLast();
        generation++;
    }

    @Override
    public String toString()
    {
         StringBuilder output = new StringBuilder("Generation ");
         output.append(generation);
         output.append(" (").append(mostFit).append(")");

         return output.toString();
    }
}
