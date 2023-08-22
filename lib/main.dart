import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'screen/home.dart';
import 'screen/map.dart';
import 'screen/tree.dart';
import 'screen/help.dart';
import 'theme_provider.dart';

void main() {
  runApp(
    ChangeNotifierProvider(
      create: (context) =>
          ThemeProvider(), // Provide the ThemeProvider instance
      child: const StateMerger(),
    ),
  );
}

class StateMerger extends StatelessWidget {
  const StateMerger({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'StateMerger',
      theme: context.watch<ThemeProvider>().currentTheme,
      home: const ScreenRoot(title: 'StateMerger Home'),
    );
  }
}

class ScreenRoot extends StatefulWidget {
  const ScreenRoot({super.key, required this.title});
  final String title;

  @override
  State<ScreenRoot> createState() => _ScreenRootState();
}

class _ScreenRootState extends State<ScreenRoot> {
  int _currentScreenIndex = 0;

  final List<Widget> _screens = const [
    HomeScreen(),
    MapScreen(),
    TreeScreen(),
    HelpScreen(),
  ];

  final List<String> _screenTitles = ["Home", "Karte", "Baum", "Hilfe"];

  void _onItemTapped(int index) {
    setState(() {
      _currentScreenIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(_screenTitles[_currentScreenIndex]),
      ),
      body: _screens[_currentScreenIndex],
      bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          currentIndex: _currentScreenIndex,
          onTap: _onItemTapped,
          items: [
            BottomNavigationBarItem(
              icon: const Icon(Icons.home),
              label: _screenTitles[0],
            ),
            BottomNavigationBarItem(
              icon: const Icon(Icons.map),
              label: _screenTitles[1],
            ),
            BottomNavigationBarItem(
              icon: const Icon(Icons.menu_open),
              label: _screenTitles[2],
            ),
            BottomNavigationBarItem(
              icon: const Icon(Icons.help),
              label: _screenTitles[3],
            )
          ]),
    );
  }
}
