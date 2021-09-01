package com.example.applicationprova

object SingletonIdProducts{
      var idList: List<String> = emptyList()
      private set

      fun addId(id: String){
            idList = idList + listOf(id)
      }
      fun removeId(id: String){
            idList = idList - listOf(id)
      }
      fun getId(): List<String>{
            return idList
      }
}