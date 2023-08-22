import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../theme_provider.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Image.asset(
              'assets/logo.png', // Replace with your logo image asset path
              width: 120,
              height: 120,
            ),
            const SizedBox(height: 16),
            const Text(
              'State Merger',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 24),
            ListTile(
              title: const Text('Nachtmodus'),
              subtitle: const Text('Setze das App-Design'),
              trailing: PopupMenuButton<String>(
                itemBuilder: (BuildContext context) => const [
                  PopupMenuItem<String>(
                    value: 'aktiviert',
                    child: Text('aktiviert'),
                  ),
                  PopupMenuItem<String>(
                    value: 'deaktiviert',
                    child: Text('deaktiviert'),
                  ),
                  PopupMenuItem<String>(
                    value: 'system',
                    child: Text('wie Systemeinstellungen'),
                  ),
                ],
                onSelected: (String value) {
                  ThemeProvider themeProvider = Provider.of<ThemeProvider>(
                      context,
                      listen: false); // Access the ThemeProvider
                  // Handle theme selection here
                  if (value == 'aktiviert') {
                    themeProvider.currentTheme =
                        ThemeData.dark(); // Change the theme
                  } else if (value == 'deaktiviert') {
                    themeProvider.currentTheme =
                        ThemeData.light(); // Change the theme
                  } else if (value == 'system') {
                    final Brightness brightness =
                        MediaQuery.of(context).platformBrightness;
                    final ThemeData theme = brightness == Brightness.dark
                        ? ThemeData.dark()
                        : ThemeData.light();

                    themeProvider.currentTheme = theme; // Change the theme
                  }
                },
              ),
            ),
            const SizedBox(height: 24),
            ListTile(
              title: const Text('Land auswählen'),
              subtitle: const Text('Wähle dein Land'),
              trailing: DropdownButton<String>(
                onChanged: (String? value) {
                  // Handle country selection here
                  // You can implement this part based on your requirements
                },
                items: [
                  DropdownMenuItem<String>(
                    value: 'country1',
                    child: Row(
                      children: [
                        Image.asset(
                            'assets/flag1.png'), // Replace with flag image asset path
                        const SizedBox(width: 8),
                        const Text('Country 1'),
                      ],
                    ),
                  ),
                  DropdownMenuItem<String>(
                    value: 'country2',
                    child: Row(
                      children: [
                        Image.asset(
                            'assets/flag2.png'), // Replace with flag image asset path
                        const SizedBox(width: 8),
                        const Text('Country 2'),
                      ],
                    ),
                  ),
                  // Add more countries as needed
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
