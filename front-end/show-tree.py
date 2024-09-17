import os

def show_tree(path, exclude_folders=None, indent='', is_last=True):
    if exclude_folders is None:
        exclude_folders = []

    try:
        items = os.listdir(path)
    except PermissionError:
        return

    # Sort directories and files separately, directories first
    items = sorted(items, key=lambda x: (os.path.isfile(os.path.join(path, x)), x))

    for idx, item in enumerate(items):
        item_path = os.path.join(path, item)
        is_last_item = idx == len(items) - 1

        if os.path.isdir(item_path):
            if item not in exclude_folders:
                connector = '└── ' if is_last_item else '├── '
                print(f"{indent}{connector}{item}/")
                # Recurse into subdirectories
                new_indent = indent + ('    ' if is_last_item else '│   ')
                show_tree(item_path, exclude_folders, new_indent, is_last_item)
        else:
            connector = '└── ' if is_last_item else '├── '
            print(f"{indent}{connector}{item}")

# Set your starting path and folders to exclude
start_path = "./"
exclude_folders = ['node_modules', '.nuxt', '.idea']

# Call the function
show_tree(start_path, exclude_folders)
