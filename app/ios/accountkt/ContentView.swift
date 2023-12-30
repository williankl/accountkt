//
//  ContentView.swift
//  accountkt
//
//  Created by Willian Mota Oliveira on 28/12/2023.
//

import SwiftUI
import KtApplication

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return ApplicationContentControllerKt.ApplicationContentController()
    }
    
    func updateUIViewController(
        _ uiViewController: UIViewController,
        context: Context
    ) {
        // Does nothing
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
    }
}
