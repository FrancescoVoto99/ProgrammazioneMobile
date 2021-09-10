import 'dart:convert';

class Gruppo {
  String NomeGruppo;
  Map<String,String> gruppo;

  Gruppo(this.NomeGruppo, this.gruppo);

  Map<String, dynamic> toJson() => {
    "nomeGruppo": NomeGruppo,
  };
}
