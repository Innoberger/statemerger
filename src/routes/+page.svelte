<!--
	This route style is inspired by:
	https://github.com/startbootstrap/startbootstrap-one-page-wonder

	Copyright (c) 2013-2023 Start Bootstrap LLC
	MIT License
-->
<header class="masthead text-center text-light">
	<div class="masthead-content">
		<div class="container px-5">
			<h1 class="masthead-heading mb-0">StateMerger</h1>
			<h2 class="masthead-subheading mb-0">Optimierung der Bundesrepublik</h2>
			<a class="btn btn-outline-light btn-lg mt-5 px-5 py-3" href="/graph">Jetzt starten</a>
		</div>
	</div>
	<div class="bg-circle-1 bg-circle"></div>
	<div class="bg-circle-2 bg-circle"></div>
	<div class="bg-circle-3 bg-circle"></div>
	<div class="bg-circle-4 bg-circle"></div>

	<div class="fixed-bottom py-5">
		Eine Projektarbeit an der <a class="link-light" target="_blank" href="https://www.h-ka.de/">Hochschule Karlsruhe</a>.
	</div>
</header>

<style>
	header.masthead {
		position: relative;
		overflow: hidden;
		padding-top: calc(10rem + 72px);
		padding-bottom: 7rem;
		height: 100vh;
		background: linear-gradient(0deg, #bfcc00 0%, #03af3c 100%);
		background-repeat: no-repeat;
		background-position: center center;
		background-attachment: scroll;
		background-size: cover;
	}
	header.masthead .masthead-content {
		z-index: 1;
		position: relative;
	}
	header.masthead .masthead-content .masthead-heading {
		font-size: 4rem;
	}
	header.masthead .masthead-content .masthead-subheading {
		font-size: 2rem;
	}
	header.masthead .bg-circle {
		z-index: 0;
		position: absolute;
		border-radius: 100%;
		background: linear-gradient(0deg, #03af3c 0%, #bfcc00 100%);
	}
	header.masthead .bg-circle-1 {
		height: 90rem;
		width: 90rem;
		bottom: -55rem;
		left: -55rem;
	}
	header.masthead .bg-circle-2 {
		height: 50rem;
		width: 50rem;
		top: -25rem;
		right: -25rem;
	}
	header.masthead .bg-circle-3 {
		height: 20rem;
		width: 20rem;
		bottom: -10rem;
		right: 5%;
	}
	header.masthead .bg-circle-4 {
		height: 30rem;
		width: 30rem;
		top: -5rem;
		right: 35%;
	}
	
	@media (min-width: 992px) {
		header.masthead {
			padding-top: calc(10rem + 55px);
			padding-bottom: 10rem;
		}
		header.masthead .masthead-content .masthead-heading {
			font-size: 6rem;
		}
		header.masthead .masthead-content .masthead-subheading {
				font-size: 4rem;
		}
	}
</style>