package com.markettwits.repository.presentation.detail.communication

import com.markettwits.core.communication.Communication
import com.markettwits.repository.presentation.detail.RepositoryUiState

interface RepositoryCommunication : Communication.Mutable<RepositoryUiState> {
    class Base : Communication.Abstract<RepositoryUiState>(), RepositoryCommunication
}