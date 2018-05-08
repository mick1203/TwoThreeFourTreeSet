set xlabel 'number of elements'
set ylabel 'height of tree'

set terminal png size 800,450

set xtics 1000

set key outside bottom left box

set title 'comparison of a Two-Three-Four-Tree`s height depending on the insertion order'
set output 'img/height-comparison.png'
plot    'dat/sorted.dat' using 1:2 with lines title 'sorted', \
        'dat/random.dat' using 1:2 with lines title 'random', \
        'dat/logarithm.dat' using 1:2 with lines title 'log(n)'
