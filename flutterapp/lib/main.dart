
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/screens/wrapper.dart';

import 'screens/groups.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  FirebaseAuth auth = FirebaseAuth.instance;

  User? user= auth.currentUser;

  if (user == null) {
    print("sono nel main, nell'if");
    runApp(MyApp());

  } else {
    print("sono nel main nell'else");
    print(user.displayName.toString());
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