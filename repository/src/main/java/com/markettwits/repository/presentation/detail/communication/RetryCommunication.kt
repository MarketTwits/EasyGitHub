package com.markettwits.repository.presentation.detail.communication

import com.markettwits.core.communication.Communication

interface RetryCommunication : Communication.Mutable<Unit> {
    class Base : Communication.SingleUiUpdate<Unit>(), RetryCommunication
}