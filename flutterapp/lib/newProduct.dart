
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/ListOfProduct.dart';
import 'package:flutterapp/Prodotto.dart';



class newProduct extends StatelessWidget {
  newProduct({Key? key, required this.idgroup}) : super(key: key);
  final String idgroup;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'InserisciProdotto',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home:MyHomePage(idgroup: idgroup),
    );
  }


}

class MyHomePage extends StatefulWidget {

  MyHomePage({Key? key, required this.idgroup}) : super(key: key);
  final String idgroup;
  @override
  _MyHomePageState createState() => _MyHomePageState(idgroup);
}

class _MyHomePageState extends State<MyHomePage> {

  String idgroup;
  _MyHomePageState(this.idgroup);

  var list = Map<String, String>();
  String dropdownValuecategoria = 'Seleziona categoria';
  String dropdownValuequantita = 'Seleziona quantità';

  final FirebaseDatabase database = FirebaseDatabase(databaseURL: "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");


  User? user= FirebaseAuth.instance.currentUser;


  final nomeProdotto = TextEditingController();
  final note = TextEditingController();




  void addProdotto() {
    DatabaseReference myRef= database.reference().child("gruppi");
    
    Prodotto prodotto=Prodotto(nomeProdotto.text.toString(), dropdownValuecategoria, dropdownValuequantita, note.text.toString(), user!.uid.toString(), user!.displayName.toString(), "0");
    String idproduct= myRef.child(idgroup).child("prodotti").push().key.toString();
    myRef.child(idgroup).child("prodotti").child(idproduct).set(prodotto.toJson());
    
  }



  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);
  @override
  Widget build(BuildContext context) {
    final nameProductField = TextField(
      obscureText: false,
      style: style,
      controller: nomeProdotto,
      decoration: InputDecoration(
          contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "Nome Prodotto",
          border:
          OutlineInputBorder(borderRadius: BorderRadius.circular(32.0))),
    );
    final categoria =DropdownButton<String>(
    value: dropdownValuecategoria,
    icon: const Icon(Icons.arrow_downward),
    iconSize: 24,
    elevation: 16,
    style: const TextStyle(color: Colors.deepPurple),
    underline: Container(
    height: 2,
    color: Colors.deepPurpleAccent,
    ),
    onChanged: (String? newValue) {
    setState(() {
    dropdownValuecategoria = newValue!;
    });
    },
    items: <String>['Seleziona categoria', 'Cibo', 'Bagno', 'Casa', 'Salute', 'Divertimento', 'Altro']
        .map<DropdownMenuItem<String>>((String value) {
    return DropdownMenuItem<String>(
    value: value,
    child: Text(value),
    );
    }).toList(),
    );
    final quantita =DropdownButton<String>(
      value: dropdownValuequantita,
      icon: const Icon(Icons.arrow_downward),
      iconSize: 24,
      elevation: 16,
      style: const TextStyle(color: Colors.deepPurple),
      underline: Container(
        height: 2,
        color: Colors.deepPurpleAccent,
      ),
      onChanged: (String? newValue) {
        setState(() {
          dropdownValuequantita = newValue!;
        });
      },
      items: <String>['Seleziona quantità', '1', '2', '3', '4', '5', '6','7','8','9','9+']
          .map<DropdownMenuItem<String>>((String value) {
        return DropdownMenuItem<String>(
          value: value,
          child: Text(value),
        );
      }).toList(),
    );


    final noteField = TextField(
      obscureText: false,
      style: style,
      controller: note,
      decoration: InputDecoration(
          contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "Note",
          border:
          OutlineInputBorder(borderRadius: BorderRadius.circular(32.0))),
    );




    final addButton = Material(
      elevation: 5.0,
      borderRadius: BorderRadius.circular(30.0),
      color: Colors.blue,
      child: MaterialButton(
        minWidth: MediaQuery.of(context).size.width,
        padding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
        onPressed: () {
          addProdotto();
          Navigator.push(context,
              MaterialPageRoute(builder: (context) => ListOfProduct(idgroup: idgroup)));
        },
        child: Text("Aggiungi",
            textAlign: TextAlign.center,
            style: style.copyWith(
                color: Colors.white, fontWeight: FontWeight.bold)),
      ),

    );



    return Scaffold(
        body: SingleChildScrollView(
          child: Center(
            child: Container(
              color: Colors.white,
              child: Padding(
                padding: const EdgeInsets.all(36.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    SizedBox(
                      height: 155.0,
                    ),
                    Text("Nome Prodotto"),
                    nameProductField,
                    SizedBox(height: 45.0),
                    categoria,
                    SizedBox(height: 45.0),
                    quantita,
                    SizedBox(height: 45.0),
                    Text("Note"),
                    noteField,
                    SizedBox(
                      height: 15.0,
                    ),
                    addButton,
                  ],
                ),
              ),
            ),
          ),
        )
    );
  }
}
Widget _buildPopupDialog(BuildContext context) {
  return new AlertDialog(
    title: const Text('Popup example'),
    content: new Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text("Hello"),
      ],
    ),
    actions: <Widget>[
      new TextButton(
        onPressed: () {
          Navigator.of(context).pop();
        },
        child: const Text('Fatto'),
      ),
    ],
  );
}