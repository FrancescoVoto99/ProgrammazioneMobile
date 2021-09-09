
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'ListOfProduct.dart';
import 'Prodotto.dart';
import 'groups.dart';

import 'Gruppo.dart';
final FirebaseDatabase database = FirebaseDatabase(databaseURL: "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");
User? user= FirebaseAuth.instance.currentUser;
String Membro="";
final nomeprodotto = TextEditingController();
final note = TextEditingController();
String dropDownValueCategory= "Cibo";
String dropDownValueQuantity= "1";
 String categoria = "";
 String quantita = "";
 String holderCategory = "";
 String holderQuantity = "";

DatabaseReference myRef = database.reference().child("gruppi");
DatabaseReference myRefutenti =database.reference().child("utentiGruppi");
FirebaseAuth auth = FirebaseAuth.instance;


class Buy extends StatelessWidget {
  Buy({Key? key, required this.idgroup}) : super(key: key);
  final String idgroup;

  @override
  Widget build(BuildContext context) {
    print('sei su buy.dart');
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Compra',
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
  void getDropDownItem(){
    setState((){
      holderCategory = dropDownValueCategory;
      holderQuantity = dropDownValueQuantity;
    });
  }
  var list = Map<String, String>();

  final FirebaseDatabase database = FirebaseDatabase(databaseURL: "https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/");


  User? user= FirebaseAuth.instance.currentUser;


  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);
  @override
  Widget build(BuildContext context) {
    final nome = TextField(
      obscureText: false,
      style: style,
      controller: nomeprodotto,
      decoration: InputDecoration(
          contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "Nome",
          border:
          OutlineInputBorder(borderRadius: BorderRadius.circular(32.0))),
    );


    final chooseCategory = DropdownButton<String>(
        value: dropDownValueCategory,
        items: [
          DropdownMenuItem(child: Text("Cibo"),
            value: "Cibo",
          ),
          DropdownMenuItem(child: Text("Bagno"),
            value: "Bagno",
          ),
          DropdownMenuItem(child: Text("Casa"),
            value: "Casa",
          ),
          DropdownMenuItem(child: Text("Salute"),
            value: "Salute",
          ),
          DropdownMenuItem(child: Text("Divertimento"),
            value: "Divertimento",
          ),
          DropdownMenuItem(child: Text("Altro"),
            value: "Altro",
          ),
        ],
        onChanged: (value){
          setState((){
            categoria = value.toString();
          });
        }
    );

    final chooseQuantity = DropdownButton<String>(items: [
      DropdownMenuItem(child: Text("1"),
        value: "1",
      ),
      DropdownMenuItem(child: Text("2"),
        value: "2",
      ),
      DropdownMenuItem(child: Text("3"),
        value: "3",
      ),
      DropdownMenuItem(child: Text("4"),
        value: "4",
      ),
      DropdownMenuItem(child: Text("5"),
        value: "5",
      ),
      DropdownMenuItem(child: Text("6"),
        value: "6",
      ),
      DropdownMenuItem(child: Text("7"),
        value: "7",
      ),
      DropdownMenuItem(child: Text("8"),
        value: "8",
      ),
      DropdownMenuItem(child: Text("9"),
        value: "9",
      ),
      DropdownMenuItem(child: Text("9+(nelle note)"),
        value: "9+(nelle note)",
      ),
    ],
        onChanged: (value){
          setState((){
            quantita = value.toString();
          });
        }
    );

    final notes = TextField(
      obscureText: false,
      style: style,
      controller: note,
      decoration: InputDecoration(

          contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "Note",
          border:
          OutlineInputBorder(borderRadius: BorderRadius.circular(32.0))),
    );

    final insertButton = Material(
      elevation: 5.0,
      borderRadius: BorderRadius.circular(30.0),
      color: Colors.blue,
      child: MaterialButton(
        minWidth: MediaQuery.of(context).size.width,
        padding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
        onPressed: () {
          _insertProduct(idgroup);
          MaterialPageRoute(builder: (context) => ListOfProduct(idgroup: idgroup,)
                           );
        },
        child: Text("INSERISCI",
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

                    Text("Nome prodotto"), nome,
                    SizedBox(height: 45.0),
                    Text("Seleziona categoria"), chooseCategory,
                    Text("Quantità"), chooseQuantity,
                    SizedBox(height: 45.0), notes,
                    SizedBox(height: 45.0), insertButton,

                  ],
                ),
              ),
            ),
          ),
        )
    );
  }
}




void _insertProduct(String idgroup) {
  if(nomeprodotto.toString() == "Nome"){
    print('Inserisci un nome valido');
  } else {
    Prodotto p = new Prodotto(
      nomeprodotto.text.toString(),
      categoria,
      quantita,
      note.text.toString(),
      user!.uid,
      user!.displayName.toString(),
      "0"
    );
    String idproduct= myRef.child(idgroup).child("prodotti").push().key.toString();
    myRef.child(idgroup).child("prodotti").child(idproduct).set(p.toJson());
    print(p.toString());
  }


}