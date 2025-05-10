//
//  sampleIOSApp.swift
//  sampleIOSApp
//
//  Created by Vladimir Raupov on 10.05.2025.
//

import SwiftUI
import CompositesSampleApp

@main
struct sampleIOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
