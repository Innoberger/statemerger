export const prerender = true;

export function load() {
	return {
		pages: [
			{
				"title": "Start",
				"external": false,
				"link": "",
				"icon": "house-door-fill",
				"description": "Einstieg in den StateMerger",
				"headline": false
			},
			{
				"title": "Graph",
				"external": false,
				"link": "graph",
				"icon": "diagram-3-fill",
				"description": "Baumansicht für den StateMerger",
				"headline": true
			},
			{
				"title": "Hilfe",
				"external": false,
				"link": "help",
				"icon": "question-lg",
				"description": "Hilfe und nützliche Informationen über den StateMerger",
				"headline": true
			},
			{
				"title": "GitHub",
				"external": true,
				"link": "https://github.com/Innoberger/statemerger",
				"icon": "github"
			}
		]
	};
}