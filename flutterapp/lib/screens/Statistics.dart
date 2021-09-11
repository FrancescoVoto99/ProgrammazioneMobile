

import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutterapp/screens/authentication/login.dart';
import 'package:pie_chart/pie_chart.dart';


class Statistics extends StatefulWidget {
  const Statistics({Key? key, required this.idgroup}) : super(key: key);
  final String idgroup;

  // final FirebaseApp app;

  @override
  StatisticsState createState() => StatisticsState(idgroup);
}


class StatisticsState extends State<Statistics> {
  String idgroup;

  late DatabaseReference searchproduct;
  List<String> categorie=["Cibo", "Bagno", "Casa", "Salute", "Divertimento", "Altro"];
  List<double> quantita=[0,0,0,0,0,0] ;



  Map<String, double> dataMap=Map();

  User? user= FirebaseAuth.instance.currentUser;

  StatisticsState(this.idgroup);


  @override
  void initState() {
    super.initState();
    final FirebaseDatabase database = FirebaseDatabase(databaseURL: "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");
    searchproduct= database.reference().child("gruppi");

    searchproduct.child(idgroup.toString()).child("prodotti").once().then((DataSnapshot? snapshot) {
      if (snapshot!.value==null) {
        setState(() {
          dataMap[""]=0;
        });


      }
      else{
        for (int i = 0; i < categorie.length; i++) {
          Map<dynamic, dynamic>.from(snapshot!.value).forEach((key, value) {
            if (value["buy"].toString() == "1" &&
                value["categoria"].toString() == categorie[i]) {
              setState(() {
                quantita[i] =
                    quantita[i] +
                        double.parse(value["quantita"].toString());
              });
            }
          });
          setState(() {
            dataMap[categorie[i]] = quantita[i];
          });
        }
    }




    });


  }



  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home:Scaffold(
        appBar: AppBar(title: Text("Lista della spesa")),
        body:PieChart( dataMap: dataMap,
          chartValuesOptions: ChartValuesOptions(
            showChartValueBackground: true,
            showChartValues: true,
            showChartValuesInPercentage: true,
            decimalPlaces: 2,
          ),),
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












