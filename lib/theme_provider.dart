import 'package:flutter/material.dart';

class ThemeProvider with ChangeNotifier {
  ThemeData _currentTheme = ThemeData.dark();

  ThemeData get currentTheme => _currentTheme;

  set currentTheme(ThemeData theme) {
    _currentTheme = theme;
    notifyListeners();
  }
}
