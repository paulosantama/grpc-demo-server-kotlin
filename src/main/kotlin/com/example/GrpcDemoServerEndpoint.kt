package com.example

import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@Singleton
class DemoServerEndpoint : GrpcDemoServerServiceGrpcKt.GrpcDemoServerServiceCoroutineImplBase() {
	override suspend fun saveUser(request: SaveUserRequest): UserResponse {
		return UserResponse.newBuilder()
			.setId(2)
			.setName(request.name)
			.setLastName(request.lastName)
			.build()
	}

	override fun saveUserStream(requests: Flow<SaveUserRequest>): Flow<UserResponse> = flow {
		var id = 2
		requests.collect {
			println("Salvando usuário...")
			emit(
				UserResponse.newBuilder()
					.setId(id++)
					.setName(it.name).setLastName(it.lastName)
					.build()
			)
			println("Concluído...")
		}
	}
}
