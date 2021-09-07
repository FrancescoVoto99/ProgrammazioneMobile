
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/newgroup.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';

class Groups extends StatefulWidget {


 // final FirebaseApp app;

  @override
  GroupsState createState() => GroupsState();
}



class GroupsState extends State<Groups> {

  late DatabaseReference myRef;
  late DatabaseReference searchgroups;
  List<String> list = List<String>.empty();
  List<String> list2 =  List<String>.empty();
  late final FirebaseApp app;

  User? user= FirebaseAuth.instance.currentUser;

  bool _anchorToBottom = false;

  String _kTestKey = 'Hello';
  String _kTestValue = 'world!';
  DatabaseError? _error;

  @override
  void initState() {
    super.initState();
    final FirebaseDatabase database = FirebaseDatabase(databaseURL: "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");
    myRef = database.reference().child("utentiGruppi");
    searchgroups= database.reference().child("gruppi");

    String? child=user?.email.toString().replaceAll('.','');
    myRef.child(child.toString()).once().then((DataSnapshot? snapshot) {
      for (var postSnapshot in snapshot!.value) {

        list.add(postSnapshot.getValue().toString());
        list2.add(postSnapshot.key.toString());


      }
    });






  }




  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("ciao")),
      body: const Center(
        child: Text('Non hai ancora nessun gruppo.'),
      ),
      drawer: Drawer(
        // Add a ListView to the drawer. This ensures the user can scroll
        // through the options in the drawer if there isn't enough vertical
        // space to fit everything.
        child: ListView(
          // Important: Remove any padding from the ListView.
          padding: EdgeInsets.zero,
          children: [
            const DrawerHeader(
              decoration: BoxDecoration(
                color: Colors.blue,
              ),
              child: Text('Statistiche'),
            ),
            ListTile(
              title: const Text('Statistica 1'),
              onTap: () {
                // Update the state of the app
                // ...
                // Then close the drawer
                Navigator.pop(context);
              },
            ),
            ListTile(
              title: const Text('Statistica 2'),
              onTap: () {
                // Update the state of the app
                // ...
                // Then close the drawer
                Navigator.pop(context);
              },
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(context,
              MaterialPageRoute(builder: (context) => NewGroup()));
        },
        child: const Icon(Icons.add),
        backgroundColor: Colors.green,
      ),
    );
  }
}





