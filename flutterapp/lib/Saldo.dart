
import 'dart:ffi';

import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/Prodotto.dart';
import 'package:flutterapp/newProduct.dart';
import 'package:flutterapp/newgroup.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutterapp/screens/authentication/login.dart';

class Saldo extends StatefulWidget {
  const Saldo({Key? key, required this.idgroup}) : super(key: key);
  final String idgroup;

  // final FirebaseApp app;

  @override
  SaldoState createState() => SaldoState(idgroup);
}





class SaldoState extends State<Saldo> {
  String idgroup;

  late DatabaseReference searchUser;
  List<String> listUtenti=[] ;
  Map<String,double> listaspese= Map<String,double>();

  User? user= FirebaseAuth.instance.currentUser;

  SaldoState(this.idgroup);

  String nomeGruppo="";






  @override
  void initState() {
    super.initState();
    final FirebaseDatabase database = FirebaseDatabase(databaseURL: "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");
    searchUser= database.reference().child("gruppi");

    //String? child=user?.email.toString().replaceAll('.','');
    searchUser.child(idgroup.toString()).child("nomeGruppo").once().then((DataSnapshot? snapshot) {
        setState(() {
          nomeGruppo=snapshot.toString();
      });
    });

    searchUser.child(idgroup.toString()).child("gruppo").once().then((DataSnapshot? snapshot) {
      Map<dynamic,dynamic>.from(snapshot!.value).forEach((key, value) {
        setState(() {

            listUtenti.add(value.toString());
            listaspese[key.toString()]=0.00 as double;
        });
      });
    });

    searchUser.child(idgroup.toString()).child("spese").once().then((DataSnapshot? snapshot) {
      Map<dynamic,dynamic>.from(snapshot!.value).forEach((key, value) {
        setState(() {
        double? spesa=listaspese[value["idutente"]];

        if(spesa != null){
          listaspese[value["idutente"].toString()] =
              spesa + double.parse(value["totale"].toString());

          }
        });
      });
    });

    CalcolaSaldo(int position)  {
      double sum = 0.00;
      listaspese.values.forEach((double e){sum += e;});
      double divisione=sum/listaspese.length;
      double dovuto= listaspese.values.toList()[position]-divisione;
      return dovuto.toString();

    }







  }
  CalcolaSaldo(int position)  {
    double sum = 0.00;
    listaspese.values.forEach((double e){sum += e;});
    double divisione=sum/listaspese.length;
    double dovuto= listaspese.values.toList()[position]-divisione;
    return dovuto.toString();

  }




  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home:Scaffold(
        appBar: AppBar(title: Text("Lista della spesa")),
        body:Column(
            children: <Widget>[
              Text('${nomeGruppo}',
                style: TextStyle(fontSize: 18),
              ),
              Expanded(
                  child: ListView.builder(
                      padding: const EdgeInsets.all(8),
                      itemCount: listUtenti.length,
                      itemBuilder: (BuildContext context, int index) {

                        return GestureDetector(
                            onTap: (){
                              print('Container clicked ${listUtenti[index]}' );
                            },
                            child:Container(
                              height: 50,
                              margin: EdgeInsets.all(2),
                              color: Colors.blue[400],
                              //  msgCount[index]>3? Colors.blue[100]: Colors.grey
                              child: Row(
                                children:<Widget> [Text('${listUtenti[index]}',
                                  style: TextStyle(fontSize: 18),
                                ),
                                  Text('${CalcolaSaldo(index)}',
                              style: TextStyle(fontSize: 18),
                            ),],

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
                child: Text('Spesa condivisa'),
              ),
              ListTile(
                title: const Text('Il mio account'),
                onTap: () {
                  // Update the state of the app
                  // ...
                  // Then close the drawer
                  Navigator.pop(context);
                },
              ),
              ListTile(
                title: const Text('Logout'),
                onTap: () {
                  signOut();
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => Login()));
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}












