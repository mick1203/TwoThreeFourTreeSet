### Node
`Node` ist eine innere Klasse die zur Organisation der Datenstruktur
verwendet wird. Gespeichert werden ein Array von Kindselementen,
ihrerseits wieder `Node`-Objekte mit einer Größe von 4 Elementen genannt
`subtrees`, ein Array von `T`-Elementen mit der Größe von 3 Elementen
genannt `landmarks`, in dem die Werte gespeichert werden. Einem `int` 
der die Anzahl an gültigen Elementen in `landmarks` angibt, genannt 
`landmarkCount`. Einer Referenz auf einen `Comparator<T>`-Objekt, welche
aber auch leer sein kann und eine Referenz auf den Elternknoten um
leichteres Traversieren des Baumes zu ermöglichen.

#### int getIndexForValue(T n)
Diese Methode gibt des Index des Kindselementes zurück in das ein
neues Element `n` eingefügt werden muss.

#### void prepareForInsertionAt(int n)
Veranlässt das `Node`-Objekt die beiden Arrays `subtrees` und `landmarks`
so zu verändern um das Einfügen an Index `n` ohne Verlust der Ordnung im
Baum zu ermöglichen.

#### boolean isLeaf()
Gibt zurück ob es sich bei dem aktuellen Knoten um einen Blattknoten
handelt, ein Knoten ist dann ein Blattknoten wenn er keine Kinder hat.

#### boolean isFull()
Gibt zurück ob der aktuelle Knoten voll ist, also ob sich im `landmarks`
Array bereits 3 Elemente befinden.

#### void split()
Veranlässt den aktuellen Knoten sich zu spalten. Dafür werden 2 neue 
Kindsknoten ersetzt, die notwendigen Daten vom aktuellen in den
richtigen der beiden Kindsknoten gespeichert, die Elternknoten der
neuen Kindsknoten geändert, das mittlere Element des aktuellen Knotens
wird in das `landmarks`-Array und die beiden Kinder in das 
`subtrees`-Array des Elternknotens eingefügt. Außerdem wird
`landmarksCount` aktualisiert um die Integrität des Baumes zu 
gewährleisten.

#### boolean add(T elem)
Fügt einen Knoten in den aktuellen Knoten ein. Mithilfe der `getIndexForValue`
Methode wird der richtige Index gesucht, handelt es sich bei dem aktuellen
Knoten um ein Blatt so wird das Element im aktuellen `landmarks` Array
persistiert. Ist dies nicht der Fall wird das Element an den nächsten passenden
Kindsknoten weitergeleitet.


#### T get(T elem)
Sucht ein Element im aktuellen Knoten und allen Kindsknoten. Zuerst wird
der aktuelle Knoten durchsucht. Wird nichts gefunden wird überprüft ob es
sich bei dem aktuellen Knoten um einen vollen Knoten handelt, wenn ja wird
er aufgeteilt. Ist der aktuelle Knoten ein Blatt wird `null` zurückgegeben
da das Element nicht gefunden wurde, ansonsten wird die Verarbeitung an den
richtigen Kindsknoten weitergeleitet. Durch das Splitten in der `get` Methode
, die einen fundamentalen Bestandteil von anderen Methoden bildet, siehe
`add` in der `TwoThreeFourTreeSet` Klasse oder `contains` in der `Node`
Klasse, wird im Baum öfters überprüft ob es volle Knoten gibt und so ein
leichteres Einfügen von Knoten ermöglicht.

#### int size()
Gibt die Anzahl an gespeicherten Elementen im aktuellen Knoten und allen
seinen Kindsknoten zurück.

#### Node getFirstNode()
Liefert das `Node`-Objekt dass in einem Baum ganz links steht.

#### T first()
Liefert das kleinste Element das in einem Baum gespeichert wurde. Die
Ordnung beruht auf dem `Comparator<T>`-Objekt oder der `compareTo`
Methode der einzelnen Objekte.

#### T last()
Liefert das größte Element das in einem Baum gespeichert wurde. Die
Ordnung beruht auf dem `Comparator<T>`-Objekt oder der `compareTo`
Methode der einzelnen Objekte.

#### int height()
Liefert die maximale Höhe eines Unterbaumes in einem Knoten.

#### String toString()
Gibt den Knoten im Format `[(Elemente){Kindsknoten}]` zurück.

#### String toNodeString()
Gibt die Elemente im Format `(Elemente)` zurück.

#### String toPHTPAString()
Gibt den Baum in einem der Datenstruktur entsprechenden Format
aus. Sprich einem Baum in der Konsole. Die Idee eine schönere
Ausgabe zur Verfügung zu stellen habe ich von einem Kollegen
übernommen. Daher auch der Name 
`Paul-Haunschmied-Tree-Printing-Algorithm-String`.

### TwoThreeFourTreeSetIterator
Der Iterator für die Datenstruktur speichert zur Berechnung des
nächsten auszugebenden Elementes den Index `i` im aktuellen Knoten
und den aktuellen Knoten selbst. Der Knoten erlaubt dabei dann das
Navigieren durch die gesamte Baumstruktur.

#### T loadCurrent()
Errechnet aus den gespeicherten Daten das aktuelle Element das
auszugeben ist. Hierbeit handelt es sich um eine Hilfsmethode
die nicht unbedingt notwendig ist.

#### boolean hasNext()
Mithilfe der zuvor beschriebenen `loadCurrent`-Methode wird überprüft
ob es noch ein Element gibt das zurückgegeben werden kann.

##### T next()
Gibt das Element zurück das den aktuell gespeicherten Daten entspricht
und aktualisiert die Daten auf das nächste Element.
Um auf das nächste Element zu springen wird überprüft ob der aktuelle
Knoten ein Blattknoten ist.

Wenn dies der Fall ist wird überprüft ob das aktuelle Element das 
Letzte in dem Knoten ist, also ob der Knoten fertig abgearbeitet wurde. 

Wenn ja wird der aktuelle Knoten auf den Elternknoten gesetzt und 
nur wenn der Elternknoten nicht `null` ist wird der Index `i` 
aktualisiert. Das wird solange wiederholt bis `i` nicht mehr der 
letzte gültige Index im Knoten ist oder bis der Knoten selbst `null` 
ist. Handelt es sich bei dem aktuellen Element um das letzte wird 
der gespeicherte Knoten auf `null` gesetzt um beim nächsten Aufruf 
von `hasNext()` `false` zurückzugeben.

Wenn das aktuelle Element nicht das letzte im Knoten ist wird einfach
der Index `i` erhöht.


Ist der aktuelle Knoten kein Blattknoten muss es zwangsläufig Kindsknoten
geben. Und da Knoten mit Kindsknoten niemals auf dem letzten Index stehen
können (das wird dadurch gewährleistet das beim Aufsteigen nach einem
vollem Blattknoten alle vollen Zwischenknoten ignoriert werden) wird einfach
der erste Knoten des nächsten Unterbaumes als aktueller Knoten gespeichert.

Am Ende wird der aktuelle Wert zurückgegeben.

### TwoThreeFourTreeSet<T extends Object>
Die Klasse die dem Benutzer zur Verfügung steht ist die Klasse
`TwoThreeFourTreeSet`, sie bildet die umgebende Klasse für `Node` und
`TwoThreeFourTreeSetIterator`.

#### int compare(T lhs, T rhs)
Wrapper-Methode um nicht vorhandene `Comparator<T>`-Objekte abzufangen.
Jeder Vergleich in der Datenstruktur wird über die `compare`-Methode
abgewickelt. Dadurch wird der Code lesbarer und es kann "unter der Haube"
auf ein vorhandenes `Comparator<T>`-Objekt reagiert werden.

#### boolean add(T elem)
Wrapper-Methode für die `add`-Methode der Klasse `Node`. Zusätzlich wird
überprüft ob das Element bereits gespeichert wurde. Implizit wird dadurch
auch die Datenstruktur aktualisiert und volle Knoten gesplittet.

#### T get(T elem)
Wrapper-Methode für die `get`-Methode der Klasse `Node`. Zusätzlich wird
hier der Sonderfall abgedeckt wenn das Wurzelelement des Baumes voll ist.
Dafür wird ein neuer leerer Elternknoten erzeugt der als neuer Wurzelknoten
dient. Zuvor wird der alte Wurzelknoten noch dazu veranlasst ein `split()`
durchzuführen.

#### boolean contains(T elem)
Überprüft ob ein Element bereits im Baum gespeichert wurde in dem nach dem
Element gesucht wird.

#### int size()
Wrapper-Methode für die `size`-Methode des Wurzelknotens `root`.

#### int first()
Wrapper-Methode für die `first`-Methode des Wurzelknotens `root`.

#### int last()
Wrapper-Methode für die `last`-Methode des Wurzelknotens `root`.

#### Comparator<T> comparator()
Gibt das `Comparator<T>`-Objekt des Wurzelknotens `root` zurück.

#### Iterator<T> iterator()
Gibt eine `Iterator<T>`-Objekt zurück das auf das erste Element im Baum
zeigt.

#### int height()
Wrapper-Methode für die `height`-Methode des Wurzelknotens `root`.

#### String toString()
Wrapper-Methode für die `toString`-Methode des Wurzelknotens `root`.

#### String toBeautifulString()
Wrapper-Methode für die `toPHTPAString`-Methode des Wurzelknotens `root`.

### Ausführen des Programmes mit ant
Das beigelegte `ant`-File funktioniert möglicherweise für das Kommando
`ant run-unit-tests` nicht da ich für `JUnit` den Pfad auf einen absoluten
ändern musste. Nichts desto trotz lege ich der Abgabe ein frisch generiertes
`jar`-File bei damit das Ausführen der `Main`-Klasse funktionert.

Ausführen des Archives mit dem folgenden Kommando, im 
Ordner `src/two-three-four-set `.

`java -jar -ea jar/two-three-four-set [optionale Argumente]`

Die `Main`-Klasse bietet folgende Funktionalität.

Keine Argumente übergeben => das Programm erzeugt `dat`-Files die zur
graphischen Aufbereitung der Höhe eines Baumes dienen. Zu finden sind diese
im Ordner `src/two-three-four-set/dat/`. In diesem Ordner befindet sich
außerdem noch eine Datei `plot.g` die mithilfe des Kommandozeilenprogramms
`gnuplot` aus den Daten ein Bild erstellt.

Übergabe einer Zahl `n` => Das Programm erzeugt einen Baum und fügt in diesen 
`n` verschiedene `Integer`-Werte ein. Bei jedem Schleifendurchlauf wird der 
aktuelle Baum als Baumstruktur in die geleerte Konsole geschrieben. Dadurch
entsteht eine Animation die den Baum beim Wachsen zeigt.

### Auswertung der Testfälle \& Ausgabe der Baumstruktur
Die Ausgabe der Testfallauswertung kann man im Ordner
`src/two-three-four-set/log/` finden.

Die Ausgabe der Baumstruktur im Dokument wurde angepasst, da bei der
Dokumentenerstellung mit LaTeX die Sonderzeichen für das Zeichnen der 
Kanten nicht korrekt angezeigt wurden. Auf der Konsole wird jedoch der
korrekte Zeichensatz verwendet.