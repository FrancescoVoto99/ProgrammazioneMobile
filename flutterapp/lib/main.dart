
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/screens/wrapper.dart';

import 'groups.dart';

void main() {
  Firebase.initializeApp();
  WidgetsFlutterBinding.ensureInitialized();
  FirebaseAuth auth = FirebaseAuth.instance;

  User? user= auth.currentUser;

    if (user == null) {
      runApp(MyApp());

    } else {
      runApp(Groups());
    }
  }



class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Wrapper(),
    );
  }


}