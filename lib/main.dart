import 'package:flutter/material.dart';

import 'screen/home.dart';
import 'screen/map.dart';
import 'screen/tree.dart';
import 'screen/help.dart';

void main() {
  runApp(const StateMerger());
}

class StateMerger extends StatelessWidget {
  const StateMerger({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'StateMerger',
      theme: ThemeData.dark(),
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

  void _onItemTapped(int index) {
    setState(() {
      _currentScreenIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _screens[_currentScreenIndex],
      bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          currentIndex: _currentScreenIndex,
          onTap: _onItemTapped,
          items: const [
            BottomNavigationBarItem(
              icon: Icon(Icons.home),
              label: 'Home',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.map),
              label: 'Karte',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.menu_open),
              label: 'Baum',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.help),
              label: 'Hilfe',
            )
          ]),
    );
  }
}
