<script lang="ts">
	import { version } from '$app/environment';
</script>

<div class="alert alert-info d-flex align-items-center mb-5" role="alert">
	<div class="me-3">
		<i class="bi-info-circle-fill"></i>
	</div>
	<div>
		Diese Webanwendung ist im Rahmen einer Projektarbeit von Fynn Arnold an der Hochschule Karlsruhe entstanden. Der Quellcode befindet sich auf <a class="alert-link" target="_blank" href="https://github.com/Innoberger/statemerger">GitHub</a>.
	</div>
</div>

<div class="mb-5">
	<section class="mb-5">
		<h3 class="mb-3">Aufgabenstellung</h3>
		<p>
			In der Bundesrepublik Deutschland wurde schon mehrmals vorgeschlagen, kleinere Bundesländer mit anderen zu fusionieren, um Verwaltungskosten zu sparen (z.B. das Saarland mit Rheinland-Pfalz oder Berlin mit Brandenburg). Angenommen, Sie möchten eine Datenstruktur implementieren, die folgende Operationen für die Städte in der Bundesrepublik beherrscht:
		</p>
		<ul>
			<li><span class="fw-bold font-monospace">Find(s)</span>: man kann für jede Stadt <span class="font-monospace">s</span> abfragen, in welchem Bundesland sie liegt</li>
			<li><span class="fw-bold font-monospace">Union(b1, b2)</span>: fusioniere die Bundesländer <span class="font-monospace">b1</span> und <span class="font-monospace">b2</span> und vergebe für das vereinte Bundesland den Namen <span class="font-monospace">b1</span> oder auch <span class="font-monospace">b2</span>. "Vereint" bedeutet, dass <span class="font-monospace">Find(s)</span> für jede Stadt aus den beiden ursprünglich beteiligten Bundesländern nun immer nur noch den einen neuen Namen zurückgibt.</li>
		</ul>
		<p>
			Damit man irgendwie auch "anfangen" kann, definieren wir noch diese Operation:
		</p>
		<ul>
			<li><span class="fw-bold font-monospace">Make-State(s, b)</span>: erstelle ein neues Bundesland mit dem Namen <span class="font-monospace">b</span>, welches nur die eine Stadt <span class="font-monospace">s</span> enthält.</li>
		</ul>
		<p>
			Zum Aufbau der "echten" heutigen Bundesländer kann man dann die Operation Union entsprechend oft aufrufen.
		</p>
		<p>
			Es ist möglich, eine Datenstruktur zu erstellen, die alle diese Operationen in so gut wie (optimaler) Linearzeit ausführt. Ihr Programm implementiert diese Datenstruktur und veranschaulicht die Arbeitsweise der einzelnen Operationen.
		</p>
		<p>
			<strong>Literatur</strong>: Th. H. Cormen et al.: <i>Introduction to Algorithms</i>, Kapitel 21
		</p>
	</section>

	<section class="mb-5">
		<h3 class="mb-3">Datenstruktur</h3>
		<p>
			Die implementierte Datenstruktur wird als <strong>Disjoint-Set Forest</strong> bezeichnet. Sie bildet eine Menge an Bäumen, von denen jedes Element nur ein einziges Mal in der gesamten Datenstruktur vorkommen darf <i>(disjunkt)</i>. Die Datenstruktur erlaubt es, die gesuchten Operationen, allgemeiner als <span class="font-monospace">Make-Set</span>, <span class="font-monospace">Union</span> und <span class="font-monospace">Make-Set</span>, in nahezu linearer Laufzeit zu implementieren.
		</p>
		<p>
			Die Datenstruktur kennt zwei Beziehungstabellen: <strong>Vorgänger</strong> und <strong>Rang</strong>. In der Vorgänger-Beziehung wird jedem Knoten ein Vorgänger zugewiesen, wobei die Wurzel eines Baumes dessen eigener Vorgänger ist. Die Rang-Beziehung weist jedem Knoten einen Rang zu, dies ist ein grund für die Effizienz der Datenstruktur. Konkret ist die Datenstruktur in der Klasse <a class="link" target="_blank" href="https://github.com/Innoberger/statemerger/blob/main/src/lib/model/forest.ts">forest.ts</a> implementiert.
		</p>
	</section>

	<section class="mb-5">
		<h3 class="mb-3">How-To</h3>
		<h5>Quick Start</h5>
		<p>
			Auf der Seite <a class="link" href="/graph">Graph</a> kann losgelegt werden. Zunächst zeigt sich nur eine Länderauswahl und die Operation <span class="font-monospace">Make-State</span>. Um alle Operationen freizuschalten gibt mehrere Optionen:
		</p>
		<ul>
			<li>Im Formular <span class="font-monospace">Make-State</span> ein Bundesland anlegen.</li>
			<li>Über den Button <i>Einstellungen</i> im Länderauswahl-Dialog ein vordefiniertes Land laden.</li>
		</ul>
		<p>
			Wird auf ein Knoten geklickt, öffnet sich ein Dialog mit Informationen über diesen Knoten. Der Knoten kann zur Vereinigung ausgewählt werden bzw. vereinigt werden, wenn vorher ein anderer Knoten zur Vereinigung ausgewählt wurde.
		</p>
		<h5>Operationen</h5>
		<p>
			Sobald ein Bundesland erzeugt oder ein vordefiniertes Bundesland geladen wurde, werden die Baumstrukutren dargestellt und alle drei Operationen ermöglicht:
		</p>
		<ul>
			<li><span class="font-monospace">Find-State</span>: Einen beliebigen Knoten suchen. Ein Knoten kann eine Stadt sein, oder auch ein Bundesland oder ein Hilfsknoten. Wird der Knoten gefunden, öffnet sich ein Dialog mit Informationen, inkl. dem Bundesland, in dem der Knoten (die Stadt) liegt. Sofern die entsprechende Anzeigeoption gewählt wurde, scrollt das Fenster zum zugehörigen Baum.</li>
			<li><span class="font-monospace">Union</span>: Vereinigt zwei Bäume (Bundesländer). Dafür müssen zwei existierende Städte oder Bundesländer (oder allgemein: Knoten) eingegeben oder ausgewählt werden. Die Vereinigung innerhalb eines Bundeslandes ist per Definition nicht möglich.</li>
			<li><span class="font-monospace">Make-State</span>: Erzeugt ein neues Bundesland mit einer einzigen Stadt. Wichtig ist, dass weder Stadt noch Bundesland bereits existieren und auch nicht gleich benannt sind.</li>
		</ul>
		<h5>Anzeigeoptionen</h5>
		<p>
			Mit einem Klick auf <i>Einstellungen</i> öffnet sich der Länderauswahl-Dialog. Zusätzlich zur Länderauswahl gibt es vier Optionen, um die Darstellung der Bäume anzupassen:
		</p>
		<ul>
			<li>Beschriftung von Hilfsknoten mit UUID anzeigen</li>
			<li>Beschriftung innerer Knoten anzeigen</li>
			<li>Ränge von Knoten anzeigen</li>
			<li>Nach Find-State zum Baum scrollen</li>
		</ul>
		<p>
			Alle Optionen sind standardmäßig deaktiviert.
		</p>
	</section>

	<section>
		<h3 class="mb-3">Community &amp; Open Source</h3>
		<p>
			Bug gefunden? Die Webanwendung weiterentwickeln? Kein Problem! Auf <a target="_blank" href="https://github.com/Innoberger/statemerger">GitHub</a> können Issues eröffnet werden, Forks erzeugt und Pull Requests angefragt werden.
		</p>
	</section>
</div>

<div class="d-flex justify-content-between mb-2">
	<div>
		Version: {version}
	</div>
</div>