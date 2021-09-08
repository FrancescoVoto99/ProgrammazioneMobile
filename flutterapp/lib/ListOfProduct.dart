
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/Prodotto.dart';
import 'package:flutterapp/newgroup.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';

class ListOfProduct extends StatefulWidget {
  const ListOfProduct({Key? key, required this.idgroup}) : super(key: key);
  final String idgroup;

  // final FirebaseApp app;

  @override
  ListOfProductState createState() => ListOfProductState(idgroup);
}



class ListOfProductState extends State<ListOfProduct> {
  String idgroup;

  late DatabaseReference searchgroups;
  List<String> list=[] ;
  List<String> list2= [] ;

  late final FirebaseApp app;

  User? user= FirebaseAuth.instance.currentUser;

  ListOfProductState(this.idgroup);

  @override
  void initState() {
    super.initState();
    final FirebaseDatabase database = FirebaseDatabase(databaseURL: "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference searchproducts= database.reference().child("gruppi");

    //String? child=user?.email.toString().replaceAll('.','');
    searchproducts.child(idgroup.toString()).child("prodotti").once().then((DataSnapshot? snapshot) {
      Map<dynamic,dynamic>.from(snapshot!.value).forEach((key, value) {
        setState(() {
          if(value['buy'].toString() == '0'){
            list.add(value['nome'].toString());
            list2.add(key.toString());
          } else {

          }


        });


      });

    });






  }




  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home:Scaffold(
        appBar: AppBar(title: Text("ciao")),
        body:Column(
            children: <Widget>[
              Expanded(
                  child: ListView.builder(
                      padding: const EdgeInsets.all(8),
                      itemCount: list.length,
                      itemBuilder: (BuildContext context, int index) {
                        return GestureDetector(
                            onTap: (){
                              print('Container clicked ${list2[index]}' );
                            },
                            child:Container(
                              height: 50,
                              margin: EdgeInsets.all(2),
                              color: Colors.blue[400],
                              //  msgCount[index]>3? Colors.blue[100]: Colors.grey
                              child: Center(
                                child: Text('${list[index]}',
                                  style: TextStyle(fontSize: 18),
                                ),

                              ),
                            ));}))]),
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
      ),
    );
  }
}





