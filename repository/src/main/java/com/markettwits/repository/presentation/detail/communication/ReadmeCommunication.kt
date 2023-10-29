package com.markettwits.repository.presentation.detail.communication

import com.markettwits.core.communication.Communication
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiState

interface ReadmeCommunication : Communication.Mutable<RepositoryReadmeUiState> {
    class Base : Communication.Abstract<RepositoryReadmeUiState>(), ReadmeCommunication
}