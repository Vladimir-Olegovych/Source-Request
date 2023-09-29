package com.gigcreator.data.models

class Server(var address: String = "0.0.0.0",
             var port: Int = 0,
             var protocol: Int = 0,
             var serverName: String = "",
             var mapName: String = "",
             var folder: String = "",
             var game: String = "",
             var appId: Int = 0,
             var playerCount: Int = 0,
             var maxPlayers: Int = 0)