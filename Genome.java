import java.util.*;

public class Genome implements Comparable<Genome>
{
    private static final char[] alphabet = new char[]
            {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
            'P','Q','R','S','T','U','V','W','X','Y','Z',' ','-','\''};

    protected MyLinkedList<Character> genes;
    private static MyLinkedList<Character> target;
    private final double mutationRate;


    public Genome()
    {
        genes = new MyLinkedList<>();
        mutationRate = 0.05;
        target = new MyLinkedList<>('K','Y','L','E','R',' ','R','O','B','I','S','O','N');
    }

    public Genome(Genome genome)
    {
        this();
        for(genome.genes.first(); genome.genes.current() != null; genome.genes.next())
        {
            genes.add(genome.genes.current());
        }
    }

    public void mutate()
    {
        Random rnd = new Random();
        if(rnd.nextInt(0, 100) < mutationRate * 100)
        {
            int position = rnd.nextInt(genes.size() + 2);
            genes.first();
            for(int i = 0; i <= position || genes.current() != null; i++)
            {
                if(i == position)
                {
                    genes.addBefore(alphabet[rnd.nextInt(0, 29)]);
                }
                genes.next();
            }
            if(genes.size() == 0)
            {
                genes.addBefore(alphabet[rnd.nextInt(0, 29)]);
            }
        }

        if(genes.size() > 1)
        {
            if(rnd.nextInt(0, 100) < mutationRate * 100)
            {
                int position = rnd.nextInt(0, genes.size());
                genes.first();
                for(int i = 0; genes.current() != null; i++)
                {
                    if(i == position)
                    {
                        genes.remove();
                    }
                    genes.next();
                }
            }
        }

        for(genes.first(); genes.current() != null; genes.next())
        {
            if(rnd.nextInt(0, 100) < mutationRate * 100)
            {
                genes.set(alphabet[rnd.nextInt(0, 29)]);
            }
        }
    }

    public void crossover(Genome parent)
    {
        Random rnd = new Random();
        parent.genes.first();
        genes.first();

        while(parent.genes.current() != null || genes.current() != null)
        {
            if(parent.genes.current() != null && rnd.nextInt(2) == 0)
            {
                if (genes.current() != null)
                {
                    genes.set(parent.genes.current());
                }
                else
                {
                    genes.add(parent.genes.current());
                }
            }
            parent.genes.next();
            genes.next();
        }
    }
    public int fitness()
    {
        int d = 0;
        target.first(); genes.first();

        //Loop for as long as it takes to iterate through the longest list
        for(int i = 0; i < target.size() || i < genes.size(); i++)
        {
            if(target.current() != null && genes.current() != null)//Both in range
            {
                if(target.current().compareTo(genes.current()) != 0)
                {
                    d++;
                }
            }
            if(target.current() == null ^ genes.current() == null)//Just one is in range
            {
                d++;
            }
            target.next(); genes.next();
        }
        int l = Math.abs(target.size() - genes.size());

        return -(l + d);
    }

    @Override
    public int compareTo(Genome o)
    {
        return fitness() - o.fitness();
    }

    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder("\"");
        for(genes.first(); genes.current() != null; genes.next())
        {
            output.append(genes.current());
        }
        output.append("\", ").append(fitness());
        return output.toString();
    }
}
