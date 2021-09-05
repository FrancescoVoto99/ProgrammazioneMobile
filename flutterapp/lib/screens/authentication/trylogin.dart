import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';

FirebaseAuth auth = FirebaseAuth.instance;

FirebaseAuth.instance
  .authStateChanges()
  .listen((User? user) {
    if(user==null= {
      print('Utente non è loggato');
    } else{
print('Utente è collegato')
}
});
