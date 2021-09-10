import 'dart:ffi';

import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/Prodotto.dart';
import 'package:flutterapp/newProduct.dart';
import 'package:flutterapp/newgroup.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';

import 'EditProduct.dart';
import 'ListOfProductBought.dart';
import 'Saldo.dart';

class ListOfShop extends StatefulWidget {
  const ListOfShop({Key? key, required this.idgroup}) : super(key: key);
  final String idgroup;

  // final FirebaseApp app;

  @override
  ListOfShopState createState() => ListOfShopState(idgroup);
}


class ListOfShopState extends State<ListOfShop> {
  String idgroup;

  late DatabaseReference searchproducts;
  List<String> nameShop = [];
  List<String> whobuy = [];
  List<double> price = [];
  List<String> idshop = [];

  List<String> utenti = [];
  
  String miaSpesa="";
  String spesaTotale="";
  


  User? user = FirebaseAuth.instance.currentUser;

  ListOfShopState(this.idgroup);
  @override
  void initState() {
    super.initState();
    final FirebaseDatabase database = FirebaseDatabase(
        databaseURL:
        "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");
    searchproducts = database.reference().child("gruppi");

    //String? child=user?.email.toString().replaceAll('.','');
    searchproducts
        .child(idgroup).child("spese").once().then((DataSnapshot? snapshot) {
      Map<dynamic, dynamic>.from(snapshot!.value).forEach((key, value) {
        setState(() {
            idshop.add(key.toString());
            nameShop.add(value["nomespesa"].toString());
            whobuy.add(value["nomeutente"].toString().toUpperCase());
            price.add(double.parse(value["totale"].toString()));
        });
      });
    });
    searchproducts
        .child(idgroup).child("gruppo").once().then((DataSnapshot? snapshot) {
      Map<dynamic, dynamic>.from(snapshot!.value).forEach((key, value) {
        setState(() {
          utenti.add(key.toString());
        });
      });
    });
    setState(() {
      double sum = 0.00;
      price.forEach((double e){sum += e;});
      spesaTotale=sum.toStringAsFixed(2);
      miaSpesa=(sum/utenti.length).toStringAsFixed(2);
    });

  }
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(title: Text("ciao")),
        body: Column(children: <Widget>[
          Expanded(
              child: ListView.builder(
                  padding: const EdgeInsets.all(8),
                  itemCount: idshop.length,
                  itemBuilder: (BuildContext context, int index) {
                    return GestureDetector(
                        onTap: () {
                            Navigator.push(context,
                                MaterialPageRoute(builder: (context) => ListOfProductBought(idgroup: idgroup,idshop: idshop[index])));
                            
                        },
                        
                        child: Container(
                      height: 50,
                      margin: EdgeInsets.all(2),
                      color: Colors.blue[400],
                      //  msgCount[index]>3? Colors.blue[100]: Colors.grey
                      child: Column(
                          children: <Widget>[
                            Row(
                    children: <Widget>[
                    Text(
                    '${nameShop[index]}',
                    style: TextStyle(fontSize: 18),
                    ),
                    Text(
                    ' ${price[index]}',
                    style: TextStyle(fontSize: 18),
                    ),
                      
                    ]
                    ),
                            Text(
                            'Acquistato da :${whobuy[index]}',
                            style: TextStyle(fontSize: 18),
                          ),
                          ]
                      ),
                    ),);})),
          Row(
            children: <Widget>[
              Row(
                children: <Widget>[
                  Text(
                    'Mia Spesa',
                    style: TextStyle(fontSize: 18),
                  ),
                  Text(
                    ' Spesa Totale',
                    style: TextStyle(fontSize: 18),
                  ),
                  
                ],
              ),
              Row(
                children: <Widget>[
                  Text(
                    '${miaSpesa}',
                    style: TextStyle(fontSize: 20),
                  ),
                  Text(
                    ' Qunatità: ${spesaTotale}',
                    style: TextStyle(fontSize: 20),
                  ),

                ],
              ),
              
            ],
          )
        ]),
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
            Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => newProduct(idgroup: idgroup)));
          },
          child: const Icon(Icons.add),
          backgroundColor: Colors.green,
        ),
      ),
    );
  }







}