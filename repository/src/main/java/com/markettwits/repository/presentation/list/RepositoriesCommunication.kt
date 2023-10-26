package com.markettwits.repository.presentation.list

import com.markettwits.core.communication.Communication

interface RepositoriesCommunication : Communication.Mutable<List<RepositoriesUiState>> {
    class Base : Communication.Abstract<List<RepositoriesUiState>>(), RepositoriesCommunication
}