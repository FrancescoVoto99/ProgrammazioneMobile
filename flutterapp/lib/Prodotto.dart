import 'dart:convert';

import 'package:object_mapper/object_mapper.dart';

class Prodotto {
  String nome;
  String categoria;
  String quantita;
  String note;
  String iduser;
  String nomeutente;
  String buy;

  Prodotto(

      this.nome,
      this.categoria,
      this.quantita,
      this.note,
      this.iduser,
      this.nomeutente,
      this.buy); // Constructor, with syntactic sugar for assignment to members.

  factory Prodotto.fromJson(dynamic json){
    return Prodotto(json['nome'] as String,
                   json['categoria'] as String,
                  json['quantita'] as String,
                  json['note'] as String,
                  json['iduser'] as String,
                  json['nomeutente'] as String,
                  json['buy'] as String);
  }
}
