import 'package:flutter/material.dart';

class TreeScreen extends StatelessWidget {
  const TreeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Tree Screen Content'),
            // Add your radio box input and dropdown here
          ],
        ),
      ),
    );
  }
}
